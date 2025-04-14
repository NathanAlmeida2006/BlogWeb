package dev.nathan.web.services.impls;

import dev.nathan.web.models.Post;
import dev.nathan.web.repositories.PostRepository;
import dev.nathan.web.services.PostService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public void criarPost(Post post) {
        postRepository.save(post);
    }

    @Override
    public List<Post> listarTodosPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post buscarPostPorId(UUID id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post com ID " + id + " não encontrado"));
    }

    @Override
    public void atualizarPost(UUID id, Post post) {
        Post existingPost = buscarPostPorId(id);
        existingPost.setAutor(post.getAutor());
        existingPost.setData(post.getData());
        existingPost.setTitulo(post.getTitulo());
        existingPost.setTexto(post.getTexto());
        postRepository.save(existingPost);
    }

    @Override
    public void deletarPost(UUID id) {
        if (!postRepository.existsById(id)) {
            throw new EntityNotFoundException("Post com ID " + id + " não encontrado");
        }
        postRepository.deleteById(id);
    }
}