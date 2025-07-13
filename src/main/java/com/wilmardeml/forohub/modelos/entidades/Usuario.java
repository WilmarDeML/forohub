package com.wilmardeml.forohub.modelos.entidades;

import com.wilmardeml.forohub.modelos.dtos.DatosRegistroUsuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String correoElectronico;
    private String contrasena;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuariosPerfiles",
            joinColumns = @JoinColumn(name = "usuarioId"),
            inverseJoinColumns = @JoinColumn(name = "perfilId"))
    private Set<Perfil> perfiles = new HashSet<>();

    public Usuario(DatosRegistroUsuario datosRegistro, String contrasenaHash) {
        nombre = datosRegistro.nombre();
        correoElectronico = datosRegistro.correoElectronico();
        contrasena = contrasenaHash;
        perfiles.addAll(datosRegistro.perfiles().stream().map(Perfil::new).toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return perfiles.stream()
                .map(perfil -> new SimpleGrantedAuthority("ROLE_".concat(perfil.getNombre())))
                .toList();
    }

    @Override
    public String getPassword() {
        return contrasena;
    }

    @Override
    public String getUsername() {
        return correoElectronico;
    }
}
