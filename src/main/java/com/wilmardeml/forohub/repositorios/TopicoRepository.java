package com.wilmardeml.forohub.repositorios;

import com.wilmardeml.forohub.modelos.entidades.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    boolean existsByTituloAndMensaje(String titulo, String mensaje);

    Page<Topico> findByCursoNombre(String nombreCurso, Pageable paginacion);
}
