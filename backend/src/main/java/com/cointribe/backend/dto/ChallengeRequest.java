package com.cointribe.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ChallengeRequest(
        @NotBlank(groups = ValidationGroups.Create.class)
        @Size(max = 255)
        String titulo,
        @Size(max = 1000)
        String descripcion,
        @Positive
        Double metaAhorro,
        @Positive
        Integer duracionDias,
        Boolean activo
) {
}
