package com.wilmardeml.forohub.servicios;

import com.wilmardeml.forohub.infra.excepciones.ValidacionException;
import com.wilmardeml.forohub.modelos.dtos.DatosDetalleTopico;
import com.wilmardeml.forohub.modelos.dtos.DatosRegistroTopico;
import com.wilmardeml.forohub.modelos.dtos.DatosTopico;
import com.wilmardeml.forohub.modelos.entidades.Topico;
import com.wilmardeml.forohub.repositorios.CursoRepository;
import com.wilmardeml.forohub.repositorios.TopicoRepository;
import com.wilmardeml.forohub.repositorios.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class TopicoService {

    private final TopicoRepository topicoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;


    public DatosDetalleTopico registrar(DatosRegistroTopico datos) {

        if (topicoRepository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje()))
            throw new ValidacionException("No puede haber un tópico con el mismo título y mensaje");

        var autor = usuarioRepository.findById(datos.autorId());
        if (autor.isEmpty())
            throw new ValidacionException("No existe el autor");

        var curso = cursoRepository.findById(datos.cursoId());
        if (curso.isEmpty())
            throw new ValidacionException("No existe el curso");

        var topico = new Topico(
                null, datos.titulo(), datos.mensaje(), LocalDateTime.now(), Boolean.TRUE, autor.get(), curso.get(),
                new ArrayList<>()
        );
        return new DatosDetalleTopico(topicoRepository.save(topico));
    }

    public Page<DatosTopico> listarTopicos(Pageable paginacion) {
        return topicoRepository.findAll(paginacion).map(DatosTopico::new);
    }

    public Page<DatosTopico> listarTopicosPorNombreCurso(String nombreCurso, Pageable paginacion) {
        return topicoRepository.findByCursoNombre(nombreCurso, paginacion).map(DatosTopico::new);
    }
}
