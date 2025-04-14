package dev.nathan.web.converters.impls;

import dev.nathan.web.converters.Converter;
import dev.nathan.web.dtos.PostDTO;
import dev.nathan.web.models.Post;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PostConverter implements Converter<Post, PostDTO> {
    private final ComentarioConverter comentarioConverter;

    public PostConverter(ComentarioConverter comentarioConverter) {
        this.comentarioConverter = comentarioConverter;
    }

    @Override
    public PostDTO toDTO(Post post) {
        return new PostDTO(
                post.getId(),
                post.getAutor(),
                post.getData(),
                post.getTitulo(),
                post.getTexto(),
                new ArrayList<>()
        );
    }

    public PostDTO toDTOWithComments(Post post) {
        return new PostDTO(
                post.getId(),
                post.getAutor(),
                post.getData(),
                post.getTitulo(),
                post.getTexto(),
                post.getComentarios() != null ?
                        post.getComentarios().stream()
                                .map(comentarioConverter::toDTO)
                                .collect(Collectors.toList()) :
                        new ArrayList<>()
        );
    }

    @Override
    public Post toEntity(PostDTO postDTO) {
        Post post = new Post();
        post.setId(postDTO.id() != null ? postDTO.id() : UUID.randomUUID());
        post.setAutor(postDTO.autor());
        post.setData(postDTO.data() != null ? postDTO.data() : LocalDateTime.now());
        post.setTitulo(postDTO.titulo());
        post.setTexto(postDTO.texto());
        return post;
    }
}