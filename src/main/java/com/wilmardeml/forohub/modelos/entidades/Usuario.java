package com.wilmardeml.forohub.modelos.entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String correoElectronico;
    private String contrasena;

    @ManyToMany
    @JoinTable(
            name = "usuarioPerfil",
            joinColumns = @JoinColumn(name = "usuarioId"),
            inverseJoinColumns = @JoinColumn(name = "perfilId"))
    private Set<Perfil> perfiles = new HashSet<>();
}
