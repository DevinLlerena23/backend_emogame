package com.emogame.emotionsapplication.Controller;

import com.emogame.emotionsapplication.Configuration.JwtUtil;
import com.emogame.emotionsapplication.Entity.Usuarios;
import com.emogame.emotionsapplication.Repository.Usuariosrepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Base64;

@RestController
@CrossOrigin("*")
public class UsuariosController {

    @Autowired
    private Usuariosrepository usuariosrepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


   /* private Key getSigningKey() {
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(SECRET_KEY));
    }*/

    @GetMapping("/usuarios")
    ResponseEntity<List<Usuarios>> all() {
        return ResponseEntity.ok(usuariosrepository.findAll());
    }

    @PostMapping("/usuarios/save")
    public ResponseEntity<Map<String, Object>> newUsuario(@RequestBody Usuarios newUsuario) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (usuariosrepository.findByCorreo(newUsuario.getCorreo()).isPresent()) {
                response.put("error", "El correo ya está registrado");
                return ResponseEntity.badRequest().body(response);
            }

            newUsuario.setContrasena(passwordEncoder.encode(newUsuario.getContrasena()));
            Usuarios savedUsuario = usuariosrepository.save(newUsuario);
            response.put("usuario", savedUsuario);
            response.put("mensaje", "Usuario registrado exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", "Error al registrar usuario: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/usuarios/{id}")
    ResponseEntity<Map<String, Object>> one(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        return usuariosrepository.findById(id)
                .map(usuario -> {
                    response.put("usuario", usuario);
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    response.put("error", "Usuario no encontrado");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                });
    }

    @PutMapping("/usuarios/{id}")
    ResponseEntity<Map<String, Object>> actualizarUsuario(@RequestBody Usuarios newUsuario, @PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        try {
            return usuariosrepository.findById(id)
                    .map(usuario -> {
                        if (newUsuario.getNombre() != null) {
                            usuario.setNombre(newUsuario.getNombre());
                        }
                        if (newUsuario.getApellido() != null) {
                            usuario.setApellido(newUsuario.getApellido());
                        }
                        if (newUsuario.getCorreo() != null) {
                            usuario.setCorreo(newUsuario.getCorreo());
                        }
                        if (newUsuario.getContrasena() != null) {
                            usuario.setContrasena(passwordEncoder.encode(newUsuario.getContrasena()));
                        }
                        if (newUsuario.getAvatar_url() != null) {
                            usuario.setAvatar_url(newUsuario.getAvatar_url());
                        }
                        Usuarios updatedUsuario = usuariosrepository.save(usuario);
                        response.put("usuario", updatedUsuario);
                        response.put("mensaje", "Usuario actualizado exitosamente");
                        return ResponseEntity.ok(response);
                    })
                    .orElseGet(() -> {
                        response.put("error", "Usuario no encontrado");
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                    });
        } catch (Exception e) {
            response.put("error", "Error al actualizar usuario");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/usuarios/{id}")
    ResponseEntity<Map<String, Object>> borrarUsuario(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (usuariosrepository.existsById(id)) {
                usuariosrepository.deleteById(id);
                response.put("mensaje", "Usuario eliminado exitosamente");
                return ResponseEntity.ok(response);
            }
            response.put("error", "Usuario no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("error", "Error al eliminar usuario");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/auth/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credentials) {
        Map<String, Object> response = new HashMap<>();
        try {
            String correo = credentials.get("correo");
            String contrasena = credentials.get("contrasena");

            if (correo == null || contrasena == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "Correo y contraseña son requeridos"));
            }

            Optional<Usuarios> usuarioOpt = usuariosrepository.findByCorreo(correo);
            if (usuarioOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Usuario no encontrado"));
            }

            Usuarios usuario = usuarioOpt.get();
            if (!passwordEncoder.matches(contrasena, usuario.getContrasena())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Contraseña incorrecta"));
            }

            String token = jwtUtil.generateToken(usuario.getCorreo());

            return ResponseEntity.ok(Map.of("message", "Login exitoso", "token", token, "usuario", usuario));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Error en el login: " + e.getMessage()));
        }
    }
}