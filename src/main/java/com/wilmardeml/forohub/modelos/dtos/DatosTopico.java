package com.wilmardeml.forohub.modelos.dtos;

import com.wilmardeml.forohub.modelos.entidades.Topico;

import java.time.LocalDateTime;

public record DatosTopico(
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        Boolean estado,
        String autor,
        String curso
) {
    public DatosTopico(Topico topico) {
        this(
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getStatus(),
                topico.getAutor().getNombre(),
                topico.getCurso().getNombre()
        );
    }
}
