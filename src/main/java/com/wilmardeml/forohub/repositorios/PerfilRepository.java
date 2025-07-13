package com.wilmardeml.forohub.repositorios;

import com.wilmardeml.forohub.modelos.entidades.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
    Perfil findByNombre(String particular);
}
