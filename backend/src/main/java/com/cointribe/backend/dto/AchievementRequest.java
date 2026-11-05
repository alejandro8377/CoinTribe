package com.cointribe.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record AchievementRequest(
        @NotBlank(groups = ValidationGroups.Create.class)
        @Size(max = 255)
        String codigo,
        @Size(max = 255)
        String icono,
        @NotBlank(groups = ValidationGroups.Create.class)
        @Size(max = 255)
        String nombre,
        @Size(max = 1000)
        String pista,
        @PositiveOrZero
        Double ahorroMinimo,
        @PositiveOrZero
        Integer leccionesMinimas,
        @PositiveOrZero
        Integer retosMinimos
) {
}
