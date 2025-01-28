package com.emogame.emotionsapplication.Repository;

import com.emogame.emotionsapplication.Entity.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Usuariosrepository extends JpaRepository<Usuarios, Integer> {
    Optional<Usuarios> findByCorreo(String correo);


}
