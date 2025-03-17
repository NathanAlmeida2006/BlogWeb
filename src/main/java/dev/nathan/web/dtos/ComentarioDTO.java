package dev.nathan.web.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.UUID;

public record ComentarioDTO(UUID id, LocalDateTime data,

                            @NotBlank(message = "O comentário não pode estar em branco.") @Size(max = 500, message = "O comentário não pode ter mais de 500 caracteres.") String comentario,

                            @NotNull(message = "O ID do post é obrigatório.") UUID postId) {
}