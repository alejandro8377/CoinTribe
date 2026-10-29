package com.cointribe.backend.dto;

public record CourseCreateRequest(String titulo, String tema, String descripcion, Integer totalLecciones) {
}
