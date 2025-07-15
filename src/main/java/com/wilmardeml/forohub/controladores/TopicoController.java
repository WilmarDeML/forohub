package com.wilmardeml.forohub.controladores;

import com.wilmardeml.forohub.modelos.dtos.DatosDetalleTopico;
import com.wilmardeml.forohub.modelos.dtos.DatosRegistroTopico;
import com.wilmardeml.forohub.modelos.dtos.DatosTopico;
import com.wilmardeml.forohub.servicios.TopicoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping
    public ResponseEntity<Page<DatosTopico>> listar(@PageableDefault(size = 10, sort = "fechaCreacion")
                                                        Pageable paginacion) {

        return ResponseEntity.ok(topicoService.listarTopicos(paginacion));
    }

    @GetMapping("curso/{nombreCurso}")
    public ResponseEntity<Page<DatosTopico>> listarPorNombreCurso(@PathVariable String nombreCurso,
                                                                  @PageableDefault(size = 10, sort = "fechaCreacion")
                                                                  Pageable paginacion) {

        return ResponseEntity.ok(topicoService.listarTopicosPorNombreCurso(nombreCurso, paginacion));
    }

    @GetMapping("{id}")
    public ResponseEntity<DatosTopico> detallar(@PathVariable Long id) {

        return ResponseEntity.ok(topicoService.detallarPorId(id));
    }

    @Transactional
    @PutMapping("{id}")
    public ResponseEntity<DatosDetalleTopico> actualizar(@RequestBody @Valid DatosRegistroTopico topico,
                                                         @PathVariable Long id) {

        var topicoActualizado = topicoService.actualizar(id, topico);
        return ResponseEntity.ok(new DatosDetalleTopico(topicoActualizado));
    }

    @Transactional
    @DeleteMapping("{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        topicoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
