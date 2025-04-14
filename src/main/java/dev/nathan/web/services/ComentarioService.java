package dev.nathan.web.services;

import dev.nathan.web.models.Comentario;

import java.util.UUID;

public interface ComentarioService {
    void criarComentario(Comentario comentario);

    Comentario buscarComentarioPorId(UUID id);

    void atualizarComentario(UUID id, Comentario comentario);

    void deletarComentario(UUID id);
}