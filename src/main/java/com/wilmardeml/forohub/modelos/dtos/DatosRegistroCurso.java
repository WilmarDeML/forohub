package com.wilmardeml.forohub.modelos.dtos;

import jakarta.validation.constraints.NotBlank;

public record DatosRegistroCurso(
        @NotBlank String nombre,
        @NotBlank String categoria
) { }
