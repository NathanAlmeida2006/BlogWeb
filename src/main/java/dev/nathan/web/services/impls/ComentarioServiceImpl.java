package dev.nathan.web.services.impls;

import dev.nathan.web.models.Comentario;
import dev.nathan.web.models.Post;
import dev.nathan.web.repositories.ComentarioRepository;
import dev.nathan.web.services.ComentarioService;
import dev.nathan.web.services.PostService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ComentarioServiceImpl implements ComentarioService {
    private final ComentarioRepository comentarioRepository;
    private final PostService postService;

    public ComentarioServiceImpl(ComentarioRepository comentarioRepository, PostService postService) {
        this.comentarioRepository = comentarioRepository;
        this.postService = postService;
    }

    @Override
    public void criarComentario(Comentario comentario) {
        Post post = postService.buscarPostPorId(comentario.getPost().getId());
        comentario.setId(comentario.getId() != null ? comentario.getId() : UUID.randomUUID());
        comentario.setData(comentario.getData() != null ? comentario.getData() : LocalDateTime.now());
        comentario.setPost(post);
        comentarioRepository.save(comentario);
    }

    @Override
    public Comentario buscarComentarioPorId(UUID id) {
        return comentarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comentário com ID " + id + " não encontrado"));
    }

    @Override
    public void atualizarComentario(UUID id, Comentario comentario) {
        Comentario existingComentario = buscarComentarioPorId(id);
        existingComentario.setComentario(comentario.getComentario());
        existingComentario.setData(comentario.getData() != null ? comentario.getData() : LocalDateTime.now());
        comentarioRepository.save(existingComentario);
    }

    @Override
    public void deletarComentario(UUID id) {
        if (!comentarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Comentário com ID " + id + " não encontrado");
        }
        comentarioRepository.deleteById(id);
    }
}