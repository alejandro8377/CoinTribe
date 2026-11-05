package com.cointribe.backend.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CourseProgressRequest(@NotNull @Min(0) @Max(100) Integer progreso) {
}
