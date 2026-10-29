package com.cointribe.backend.dto;

import com.cointribe.backend.models.Achievement;

public record AchievementResponseDto(
        Long id,
        String codigo,
        String icono,
        String nombre,
        String pista,
        Double ahorroMinimo,
        Integer leccionesMinimas,
        Integer retosMinimos
) {

    public static AchievementResponseDto from(Achievement achievement) {
        return new AchievementResponseDto(
                achievement.getId(),
                achievement.getCodigo(),
                achievement.getIcono(),
                achievement.getNombre(),
                achievement.getPista(),
                achievement.getAhorroMinimo(),
                achievement.getLeccionesMinimas(),
                achievement.getRetosMinimos()
        );
    }
}
