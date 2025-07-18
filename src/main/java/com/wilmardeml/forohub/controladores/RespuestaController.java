package com.wilmardeml.forohub.controladores;

import com.wilmardeml.forohub.modelos.dtos.*;
import com.wilmardeml.forohub.servicios.RespuestaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@SecurityRequirement(name = "bearer-key")
@RestController
@RequestMapping("respuestas")
public class RespuestaController {

    @Autowired
    private RespuestaService respuestaService;

    @Transactional
    @PostMapping
    public ResponseEntity<DatosDetalleRespuesta> registrar(@RequestBody @Valid DatosRegistroRespuesta datosRespuesta) {

        DatosDetalleRespuesta detalleRespuesta = respuestaService.registrar(datosRespuesta);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(detalleRespuesta.id())
                .toUri();

        return ResponseEntity.created(location).body(detalleRespuesta);
    }

    @Transactional
    @PutMapping("{id}")
    public ResponseEntity<DatosDetalleRespuesta> actualizar(@RequestBody @Valid DatosRegistroRespuesta topico,
                                                         @PathVariable Long id) {

        var respuestaActualizada = respuestaService.actualizar(id, topico);
        return ResponseEntity.ok(new DatosDetalleRespuesta(respuestaActualizada));
    }

}
