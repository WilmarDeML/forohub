package com.wilmardeml.forohub.servicios;

import com.wilmardeml.forohub.infra.excepciones.ValidacionException;
import com.wilmardeml.forohub.modelos.dtos.DatosDetalleRespuesta;
import com.wilmardeml.forohub.modelos.dtos.DatosRegistroRespuesta;
import com.wilmardeml.forohub.modelos.entidades.Respuesta;
import com.wilmardeml.forohub.modelos.entidades.Topico;
import com.wilmardeml.forohub.modelos.entidades.Usuario;
import com.wilmardeml.forohub.repositorios.RespuestaRepository;
import com.wilmardeml.forohub.repositorios.TopicoRepository;
import com.wilmardeml.forohub.repositorios.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RespuestaService {

    private final RespuestaRepository respuestaRepository;
    private final TopicoRepository topicoRepository;
    private final UsuarioRepository usuarioRepository;


    public DatosDetalleRespuesta registrar(DatosRegistroRespuesta datos) {

        validarMensaje(datos);

        var topico = obtenerTopicoSiExiste(datos.topicoId());
        var autor = obtenerAutorSiExiste(datos);
        var solucion = datos.solucion() != null ? datos.solucion() : false;

        var respuesta = new Respuesta(null, datos.mensaje(), topico, LocalDateTime.now(), autor, solucion);

        return new DatosDetalleRespuesta(respuestaRepository.save(respuesta));
    }

    public Respuesta actualizar(Long id, DatosRegistroRespuesta respuesta) {
        validarMensajeAlActualizar(id, respuesta);
        var respuestaBuscada = obtenerRespuestaSiExiste(id);
        var autor = obtenerAutorSiExiste(respuesta);
        respuestaBuscada.actualizar(respuesta, autor, respuesta.solucion());
        return respuestaBuscada;
    }

    private Respuesta obtenerRespuestaSiExiste(Long id) {
        return respuestaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("La respuesta no existe"));
    }

    private Topico obtenerTopicoSiExiste(Long id) {
        return topicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El tópico no existe"));
    }

    private Usuario obtenerAutorSiExiste(DatosRegistroRespuesta respuesta) {
        return usuarioRepository.findById(respuesta.autorId())
                .orElseThrow(() -> new ValidacionException("No existe el autor"));
    }

    private void validarMensaje(DatosRegistroRespuesta respuesta) {
        if (respuestaRepository.existsByMensajeAndTopicoId(respuesta.mensaje(), respuesta.topicoId()))
            throw new ValidacionException("Ya existe una respuesta con el mismo mensaje para este tópico");
    }

    private void validarMensajeAlActualizar(Long id, DatosRegistroRespuesta respuesta) {
        if (respuestaRepository.existsByMensajeAndTopicoIdAndIdNot(respuesta.mensaje(), respuesta.topicoId(), id))
            throw new ValidacionException("Ya existe una respuesta con el mismo mensaje para este tópico");
    }
}
