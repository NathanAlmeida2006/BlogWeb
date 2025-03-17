package dev.nathan.web.services.impls;

import dev.nathan.web.models.Comentario;
import dev.nathan.web.repositories.ComentarioRepository;
import dev.nathan.web.services.ComentarioService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ComentarioServiceImpl implements ComentarioService {

    private final ComentarioRepository comentarioRepository;

    public ComentarioServiceImpl(ComentarioRepository comentarioRepository) {
        this.comentarioRepository = comentarioRepository;
    }

    @Override
    public Comentario criarComentario(Comentario comentario) {
        return comentarioRepository.save(comentario);
    }

    @Override
    public Comentario buscarComentarioPorId(UUID id) {
        return comentarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Comentário não encontrado"));
    }

    @Override
    public List<Comentario> listarTodosComentarios() {
        Iterable<Comentario> comentariosIterable = comentarioRepository.findAll();
        List<Comentario> comentariosList = new ArrayList<>();
        comentariosIterable.forEach(comentariosList::add);
        return comentariosList;
    }

    @Override
    public Comentario atualizarComentario(UUID id, Comentario comentario) {
        Comentario comentarioExistente = comentarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Comentário não encontrado"));
        comentarioExistente.setComentario(comentario.getComentario());
        return comentarioRepository.save(comentarioExistente);
    }

    @Override
    public void deletarComentario(UUID id) {
        comentarioRepository.deleteById(id);
    }
}
