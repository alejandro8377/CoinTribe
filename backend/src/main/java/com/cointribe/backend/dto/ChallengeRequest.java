package com.cointribe.backend.dto;

public record ChallengeRequest(
        String titulo,
        String descripcion,
        Double metaAhorro,
        Integer duracionDias,
        Boolean activo
) {
}
