package com.wilmardeml.forohub.modelos.entidades;

import com.wilmardeml.forohub.modelos.dtos.DatosRegistroCurso;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cursos")
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String categoria;

    public Curso(DatosRegistroCurso datos) {
        nombre = datos.nombre();
        categoria = datos.categoria();
    }
}
