package com.cointribe.backend.dto;

import jakarta.validation.constraints.Size;

public record PostRequest(
        @Size(max = 255) String autor,
        @Size(max = 255) String etiqueta,
        @Size(min = 5, max = 255)
        String titulo,
        @Size(min = 10, max = 2000)
        String cuerpo
) {
}
