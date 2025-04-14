package dev.nathan.web.services;

import dev.nathan.web.models.Post;

import java.util.List;
import java.util.UUID;

public interface PostService {
    void criarPost(Post post);

    List<Post> listarTodosPosts();

    Post buscarPostPorId(UUID id);

    void atualizarPost(UUID id, Post post);

    void deletarPost(UUID id);
}