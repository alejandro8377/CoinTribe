package com.cointribe.backend.dto;

public record AchievementRequest(
        String codigo,
        String icono,
        String nombre,
        String pista,
        Double ahorroMinimo,
        Integer leccionesMinimas,
        Integer retosMinimos
) {
}
