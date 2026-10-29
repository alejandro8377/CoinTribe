package com.cointribe.backend.dto;

import com.cointribe.backend.models.Challenge;

public record ChallengeResponseDto(
        Long id,
        String titulo,
        String descripcion,
        Double metaAhorro,
        Integer duracionDias,
        Boolean activo
) {

    public static ChallengeResponseDto from(Challenge challenge) {
        return new ChallengeResponseDto(
                challenge.getId(),
                challenge.getTitulo(),
                challenge.getDescripcion(),
                challenge.getMetaAhorro(),
                challenge.getDuracionDias(),
                challenge.getActivo()
        );
    }
}
