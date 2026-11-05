package com.cointribe.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record CourseCreateRequest(
        @NotBlank @Size(max = 255) String titulo,
        @Size(max = 255) String tema,
        @Size(max = 1000) String descripcion,
        @PositiveOrZero Integer totalLecciones
) {
}
