package dev.nathan.web.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record PostDTO(UUID id,

                      @NotBlank(message = "Autor não pode estar vazio") @Size(min = 1, max = 100, message = "Autor deve ter entre 1 e 100 caracteres") String autor,

                      LocalDateTime data,

                      @NotBlank(message = "Título não pode estar vazio") @Size(min = 1, max = 200, message = "Título deve ter entre 1 e 200 caracteres") String titulo,

                      @NotBlank(message = "Texto não pode estar vazio") @Size(min = 1, max = 10000, message = "Texto deve ter entre 1 e 10000 caracteres") String texto,

                      List<ComentarioDTO> comentarios) {
}