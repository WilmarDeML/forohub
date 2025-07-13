package com.wilmardeml.forohub.controladores;

import com.wilmardeml.forohub.modelos.dtos.DatosDetalleUsuario;
import com.wilmardeml.forohub.modelos.dtos.DatosRegistroUsuario;
import com.wilmardeml.forohub.servicios.UsuarioService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

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

}
