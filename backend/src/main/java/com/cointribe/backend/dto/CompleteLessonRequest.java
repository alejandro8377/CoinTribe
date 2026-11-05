package com.cointribe.backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CompleteLessonRequest(
        @NotNull @Positive Long userId,
        @NotNull @Positive Integer lecciones
) {
}
