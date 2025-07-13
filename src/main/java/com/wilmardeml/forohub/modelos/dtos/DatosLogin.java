package com.wilmardeml.forohub.modelos.dtos;

import jakarta.validation.constraints.NotBlank;

public record DatosLogin(
        @NotBlank String correoElectronico,
        @NotBlank String contrasena
) {
}
