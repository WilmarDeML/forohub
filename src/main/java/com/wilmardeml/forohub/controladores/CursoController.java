package com.wilmardeml.forohub.controladores;

import com.wilmardeml.forohub.modelos.dtos.DatosDetalleCurso;
import com.wilmardeml.forohub.modelos.dtos.DatosRegistroCurso;
import com.wilmardeml.forohub.servicios.CursoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@SecurityRequirement(name = "bearer-key")
@RestController
@RequiredArgsConstructor
@RequestMapping("cursos")
public class CursoController {

    private final CursoService cursoService;

    @PreAuthorize("hasAnyRole('admin')")
    @Transactional
    @PostMapping
    public ResponseEntity<DatosDetalleCurso> registrar(@RequestBody @Valid DatosRegistroCurso datos) {

        DatosDetalleCurso detalleCurso = cursoService.registrar(datos);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(detalleCurso.id())
                .toUri();

        return ResponseEntity.created(location).body(detalleCurso);
    }

}
