package dev.nathan.web.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record PostDTO(UUID id,

                      @NotBlank(message = "O autor não pode estar em branco.") @Size(max = 70, message = "O autor não pode ter mais de 70 caracteres.") String autor,

                      LocalDateTime data,

                      @NotBlank(message = "O título não pode estar em branco.") @Size(max = 70, message = "O título não pode ter mais de 70 caracteres.") String titulo,

                      @NotBlank(message = "O texto não pode estar em branco.") String texto,

                      List<ComentarioDTO> comentarios) {
}