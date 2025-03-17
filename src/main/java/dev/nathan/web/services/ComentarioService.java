package dev.nathan.web.services;

import dev.nathan.web.models.Comentario;

import java.util.List;
import java.util.UUID;

public interface ComentarioService {
    Comentario criarComentario(Comentario comentario);

    Comentario buscarComentarioPorId(UUID id);

    List<Comentario> listarTodosComentarios();

    Comentario atualizarComentario(UUID id, Comentario comentario);

    void deletarComentario(UUID id);
}
