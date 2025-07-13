package com.wilmardeml.forohub.servicios;

import com.wilmardeml.forohub.infra.excepciones.ValidacionException;
import com.wilmardeml.forohub.modelos.dtos.*;
import com.wilmardeml.forohub.modelos.entidades.Curso;
import com.wilmardeml.forohub.repositorios.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    public DatosDetalleCurso registrar(DatosRegistroCurso datos) {

        if (cursoRepository.existsByNombreAndCategoria(datos.nombre(), datos.categoria()))
            throw new ValidacionException("Ya existe un curso con el nombre y categoría indicados");

        return new DatosDetalleCurso(cursoRepository.save(new Curso(datos)));
    }
}
