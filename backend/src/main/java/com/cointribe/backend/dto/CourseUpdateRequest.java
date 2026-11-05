package com.cointribe.backend.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record CourseUpdateRequest(
        @Size(max = 255)
        String titulo,
        @Size(max = 255)
        String tema,
        @Size(max = 1000)
        String descripcion,
        @PositiveOrZero
        Integer totalLecciones,
        @Min(0) @Max(100)
        Integer progreso
) {
}
