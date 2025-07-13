package com.wilmardeml.forohub.modelos.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;

public record DatosRegistroUsuario(
        @NotBlank String nombre,
        @NotBlank String contrasena,
        @NotBlank @Email String correoElectronico,
        Set<DatosPerfil> perfiles
) {
    public DatosRegistroUsuario {
        if (perfiles == null)
            perfiles = new HashSet<>();
    }
}
