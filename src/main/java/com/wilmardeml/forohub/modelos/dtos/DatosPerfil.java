package com.wilmardeml.forohub.modelos.dtos;

import com.wilmardeml.forohub.modelos.entidades.Perfil;

public record DatosPerfil(Long id, String nombre) {

    public DatosPerfil(Perfil perfil) {
        this (
                perfil.getId(),
                perfil.getNombre()
        );
    }
}
