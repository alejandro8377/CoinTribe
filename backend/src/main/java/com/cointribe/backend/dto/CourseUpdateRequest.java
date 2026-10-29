package com.cointribe.backend.dto;

public record CourseUpdateRequest(
        String titulo,
        String tema,
        String descripcion,
        Integer totalLecciones,
        Integer progreso
) {
}
