package com.wilmardeml.forohub.servicios;

import com.wilmardeml.forohub.infra.excepciones.ValidacionException;
import com.wilmardeml.forohub.modelos.dtos.DatosDetalleTopico;
import com.wilmardeml.forohub.modelos.dtos.DatosRegistroTopico;
import com.wilmardeml.forohub.modelos.dtos.DatosTopico;
import com.wilmardeml.forohub.modelos.entidades.Curso;
import com.wilmardeml.forohub.modelos.entidades.Topico;
import com.wilmardeml.forohub.modelos.entidades.Usuario;
import com.wilmardeml.forohub.repositorios.CursoRepository;
import com.wilmardeml.forohub.repositorios.TopicoRepository;
import com.wilmardeml.forohub.repositorios.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
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

        validarTituloMensaje(datos);

        var autor = obtenerAutorSiExiste(datos);
        var curso = obtenerCursoSiExiste(datos);

        var topico = new Topico(null, datos.titulo(), datos.mensaje(), LocalDateTime.now(), Boolean.TRUE, autor,
                curso, new ArrayList<>());

        return new DatosDetalleTopico(topicoRepository.save(topico));
    }

    public Page<DatosTopico> listarTopicos(Pageable paginacion) {
        return topicoRepository.findAll(paginacion).map(DatosTopico::new);
    }

    public Page<DatosTopico> listarTopicosPorNombreCurso(String nombreCurso, Pageable paginacion) {
        return topicoRepository.findByCursoNombre(nombreCurso, paginacion).map(DatosTopico::new);
    }

    public DatosTopico detallarPorId(Long idTopico) {
        return new DatosTopico(topicoRepository.getReferenceById(idTopico));
    }

    public Topico actualizar(Long id, DatosRegistroTopico topico) {
        var topicoBuscado = topicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El tópico no existe"));
        validarTituloMensaje(topico);
        var autor = obtenerAutorSiExiste(topico);
        var curso = obtenerCursoSiExiste(topico);
        topicoBuscado.actualizar(topico, autor, curso);
        return topicoBuscado;
    }

    private Usuario obtenerAutorSiExiste(DatosRegistroTopico topico) {
        return usuarioRepository.findById(topico.autorId())
                .orElseThrow(() -> new ValidacionException("No existe el autor"));
    }

    private Curso obtenerCursoSiExiste(DatosRegistroTopico topico) {
        return cursoRepository.findById(topico.cursoId())
                .orElseThrow(() -> new ValidacionException("No existe el curso"));
    }

    private void validarTituloMensaje(DatosRegistroTopico topico) {
        if (topicoRepository.existsByTituloAndMensaje(topico.titulo(), topico.mensaje()))
            throw new ValidacionException("Ya existe el tópico con el mismo título y mensaje");
    }
}
