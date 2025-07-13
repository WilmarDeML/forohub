package com.wilmardeml.forohub.controladores;

import com.wilmardeml.forohub.modelos.dtos.DatosDetalleTopico;
import com.wilmardeml.forohub.modelos.dtos.DatosRegistroTopico;
import com.wilmardeml.forohub.servicios.TopicoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("topicos")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;

    @Transactional
    @PostMapping
    public ResponseEntity<DatosDetalleTopico> registrar(@RequestBody @Valid DatosRegistroTopico datos) {

        DatosDetalleTopico detalleTopico = topicoService.registrar(datos);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(detalleTopico.id())
                .toUri();

        return ResponseEntity.created(location).body(detalleTopico);
    }

}
