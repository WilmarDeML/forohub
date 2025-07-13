package com.wilmardeml.forohub.modelos.entidades;

import com.wilmardeml.forohub.modelos.dtos.DatosPerfil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "perfiles")
public class Perfil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @ManyToMany(mappedBy = "perfiles")
    private Set<Usuario> usuarios = new HashSet<>();

    public Perfil(DatosPerfil datosPerfil) {
        id = datosPerfil.id();
        nombre = datosPerfil.nombre();
    }
}
