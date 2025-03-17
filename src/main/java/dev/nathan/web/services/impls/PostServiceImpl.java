package dev.nathan.web.services.impls;

import dev.nathan.web.models.Post;
import dev.nathan.web.repositories.PostRepository;
import dev.nathan.web.services.PostService;
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
    public Post criarPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post buscarPostPorId(UUID id) {
        return postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post não encontrado"));
    }

    @Override
    public List<Post> listarTodosPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post atualizarPost(UUID id, Post post) {
        Post postExistente = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post não encontrado"));
        postExistente.setAutor(post.getAutor());
        postExistente.setTitulo(post.getTitulo());
        postExistente.setTexto(post.getTexto());
        return postRepository.save(postExistente);
    }

    @Override
    public void deletarPost(UUID id) {
        postRepository.deleteById(id);
    }
}
