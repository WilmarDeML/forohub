package com.wilmardeml.forohub.repositorios;

import com.wilmardeml.forohub.modelos.entidades.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
    boolean existsByNombreAndCategoria(String nombre, String categoria);
}
