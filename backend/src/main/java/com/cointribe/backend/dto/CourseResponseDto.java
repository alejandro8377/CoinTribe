package com.cointribe.backend.dto;

import com.cointribe.backend.models.Course;

public record CourseResponseDto(
        Long id,
        String titulo,
        String tema,
        String descripcion,
        Integer progreso,
        Integer totalLecciones
) {

    public static CourseResponseDto from(Course course) {
        return new CourseResponseDto(
                course.getId(),
                course.getTitulo(),
                course.getTema(),
                course.getDescripcion(),
                course.getProgreso(),
                course.getTotalLecciones()
        );
    }
}
