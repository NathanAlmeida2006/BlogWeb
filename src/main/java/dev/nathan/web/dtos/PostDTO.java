package dev.nathan.web.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record PostDTO(
        UUID id,
        @NotBlank(message = "Autor é obrigatório")
        @Size(min = 2, max = 100, message = "Autor deve ter entre 2 e 100 caracteres")
        String autor,
        LocalDateTime data,
        @NotBlank(message = "Título é obrigatório")
        @Size(min = 3, max = 200, message = "Título deve ter entre 3 e 200 caracteres")
        String titulo,
        @NotBlank(message = "Texto é obrigatório")
        @Size(min = 10, message = "Texto deve ter no mínimo 10 caracteres")
        String texto,
        List<ComentarioDTO> comentarios
) {
}