package com.wilmardeml.forohub.controladores;

import com.wilmardeml.forohub.modelos.dtos.*;
import com.wilmardeml.forohub.modelos.entidades.Usuario;
import com.wilmardeml.forohub.servicios.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("login")
public class AutenticacionController {

    private final AuthenticationManager manager;
    private final TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> iniciarSesion(@RequestBody @Valid DatosLogin datosLogin) {

        var authToken = new UsernamePasswordAuthenticationToken(datosLogin.correoElectronico(), datosLogin.contrasena());
        var autenticacion = manager.authenticate(authToken);

        var JWToken = tokenService.generarToken((Usuario) autenticacion.getPrincipal());
        return ResponseEntity.ok(new DatosJWToken(JWToken));
    }
}

record DatosJWToken(String token) {}
