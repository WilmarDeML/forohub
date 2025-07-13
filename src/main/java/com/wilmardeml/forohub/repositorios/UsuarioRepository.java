package com.wilmardeml.forohub.repositorios;

import com.wilmardeml.forohub.modelos.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByCorreoElectronico(String correo);

    Optional<Usuario> findByCorreoElectronico(String correo);
}
