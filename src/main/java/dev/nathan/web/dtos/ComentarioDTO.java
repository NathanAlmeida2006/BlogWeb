package dev.nathan.web.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.UUID;

public record ComentarioDTO(
        UUID id,
        LocalDateTime data,
        @NotBlank(message = "Comentário não pode estar vazio")
        @Size(min = 1, max = 1000, message = "Comentário deve ter entre 1 e 1000 caracteres")
        String comentario,
        UUID postId
) {
}