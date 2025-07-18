package com.wilmardeml.forohub.controladores;

import com.wilmardeml.forohub.modelos.dtos.DatosDetalleUsuario;
import com.wilmardeml.forohub.modelos.dtos.DatosLogin;
import com.wilmardeml.forohub.modelos.dtos.DatosRegistroUsuario;
import com.wilmardeml.forohub.modelos.entidades.Usuario;
import com.wilmardeml.forohub.servicios.TokenService;
import com.wilmardeml.forohub.servicios.UsuarioService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager manager;
    private final TokenService tokenService;

    @Transactional
    @PostMapping
    public ResponseEntity<DatosDetalleUsuario> registrar(@RequestBody @Valid DatosRegistroUsuario datos) {

        DatosDetalleUsuario detalleUsuario = usuarioService.registrar(datos);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(detalleUsuario.id())
                .toUri();

        return ResponseEntity.created(location).body(detalleUsuario);
    }

    @PostMapping("login")
    public ResponseEntity<?> iniciarSesion(@RequestBody @Valid DatosLogin datosLogin) {

        var authToken = new UsernamePasswordAuthenticationToken(datosLogin.correoElectronico(), datosLogin.contrasena());
        var autenticacion = manager.authenticate(authToken);

        var JWToken = tokenService.generarToken((Usuario) autenticacion.getPrincipal());
        return ResponseEntity.ok(new DatosJWToken(JWToken));
    }

}

record DatosJWToken(String token) {}
