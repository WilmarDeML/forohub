package com.wilmardeml.forohub.servicios;

import com.wilmardeml.forohub.infra.excepciones.ValidacionException;
import com.wilmardeml.forohub.modelos.dtos.*;
import com.wilmardeml.forohub.modelos.entidades.Perfil;
import com.wilmardeml.forohub.modelos.entidades.Usuario;
import com.wilmardeml.forohub.repositorios.PerfilRepository;
import com.wilmardeml.forohub.repositorios.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;

    public DatosDetalleUsuario registrar(DatosRegistroUsuario datos) {

        if (usuarioRepository.existsByCorreoElectronico(datos.correoElectronico()))
            throw new ValidacionException("El correo ya ha sido registrado con otro usuario");

        if (datos.perfiles().isEmpty()) {
            var perfilRegistrado = perfilRepository.findByNombre("registrado");
            datos.perfiles().add(perfilRegistrado != null
                    ? new DatosPerfil(perfilRegistrado)
                    : new DatosPerfil(perfilRepository.save(new Perfil(new DatosPerfil(null, "particular")))));
        } else {
            for (DatosPerfil datosPerfil : datos.perfiles()) {
                var perfil = perfilRepository.findById(datosPerfil.id());
                if (perfil.isEmpty())
                    throw new ValidacionException("Perfíl inválido '".concat(datosPerfil.nombre()).concat("'"));

            }
        }

        var contrasenaHash = passwordEncoder.encode(datos.contrasena());
        var usuario = new Usuario(datos, contrasenaHash);

        return new DatosDetalleUsuario(usuarioRepository.save(usuario));
    }
}
