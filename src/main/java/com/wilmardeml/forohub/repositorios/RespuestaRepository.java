package com.wilmardeml.forohub.repositorios;

import com.wilmardeml.forohub.modelos.entidades.Respuesta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {
    boolean existsByMensajeAndTopicoId(String mensaje, Long topicoId);

    boolean existsByMensajeAndTopicoIdAndIdNot(String mensaje, Long topicoId, Long id);
}
