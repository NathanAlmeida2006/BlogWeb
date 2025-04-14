package dev.nathan.web.converters.impls;

import dev.nathan.web.converters.Converter;
import dev.nathan.web.dtos.ComentarioDTO;
import dev.nathan.web.models.Comentario;
import dev.nathan.web.models.Post;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class ComentarioConverter implements Converter<Comentario, ComentarioDTO> {
    @Override
    public ComentarioDTO toDTO(Comentario comentario) {
        return new ComentarioDTO(
                comentario.getId(),
                comentario.getData(),
                comentario.getComentario(),
                comentario.getPost().getId()
        );
    }

    @Override
    public Comentario toEntity(ComentarioDTO comentarioDTO) {
        Comentario comentario = new Comentario();
        comentario.setId(comentarioDTO.id() != null ? comentarioDTO.id() : UUID.randomUUID());
        comentario.setData(comentarioDTO.data() != null ? comentarioDTO.data() : LocalDateTime.now());
        comentario.setComentario(comentarioDTO.comentario());
        return comentario;
    }

    public Comentario toEntity(ComentarioDTO comentarioDTO, Post post) {
        Comentario comentario = toEntity(comentarioDTO);
        comentario.setPost(post);
        return comentario;
    }
}