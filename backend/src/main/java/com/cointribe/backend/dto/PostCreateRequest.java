package com.cointribe.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostCreateRequest(
        @NotBlank @Size(max = 255) String autor,
        @Size(max = 255) String etiqueta,
        @NotBlank @Size(min = 5, max = 255) String titulo,
        @NotBlank @Size(min = 10, max = 2000) String cuerpo
) {
}
