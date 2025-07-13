package com.wilmardeml.forohub.modelos.dtos;

import com.wilmardeml.forohub.modelos.entidades.Usuario;

public record DatosDetalleUsuario(
        Long id,
        String nombre
) {
    public DatosDetalleUsuario(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getNombre()
        );
    }
}
