package com.emogame.emotionsapplication.Controller;

import com.emogame.emotionsapplication.Entity.Usuarios;
import com.emogame.emotionsapplication.Repository.Usuariosrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin("*")
public class UsuariosController {
    @Autowired
    private Usuariosrepository usuariosrepository;



    @GetMapping("/usuarios")
    ResponseEntity<List<Usuarios>> all() {
        return ResponseEntity.ok(usuariosrepository.findAll());
    }

    @PostMapping("/usuarios/save")
    ResponseEntity<Map<String, Object>> newUsuario(@RequestBody Usuarios newUsuario) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (usuariosrepository.findByCorreo(newUsuario.getCorreo()).isPresent()) {
                response.put("error", "El correo ya está registrado");
                return ResponseEntity.badRequest().body(response);
            }


            Usuarios savedUsuario = usuariosrepository.save(newUsuario);
            response.put("usuarios", savedUsuario);
            response.put("mensaje", "Usuario registrado exitosamente");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", "Error al registrar usuario");
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
                            usuario.setContrasena(newUsuario.getContrasena());
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


    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        try {
            String correo = credentials.get("correo");
            String contrasena = credentials.get("contrasena");

            if (correo == null || contrasena == null) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "Correo y contraseña son requeridos");
                return ResponseEntity.badRequest().body(response);
            }

            // Buscar usuario por correo
            Optional<Usuarios> usuarioOpt = usuariosrepository.findByCorreo(correo);

            if (!usuarioOpt.isPresent()) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "Usuario no encontrado");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            Usuarios usuario = usuarioOpt.get();

            // Verificar contraseña (asumiendo que no está encriptada por ahora)
            if (!usuario.getContrasena().equals(contrasena)) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "Contraseña incorrecta");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            // Si todo está bien, devolver éxito
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login exitoso");
            response.put("usuario", usuario);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error en el login: " + e.getMessage());
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


}
