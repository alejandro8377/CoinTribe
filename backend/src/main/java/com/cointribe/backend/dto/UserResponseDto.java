package com.cointribe.backend.dto;

import com.cointribe.backend.models.User;

import java.time.LocalDateTime;

public record UserResponseDto(
        Long id,
        String nombre,
        String email,
        Integer nivel,
        Double ahorro,
        Double meta,
        Integer leccionesCompletadas,
        Integer retosAceptados,
        LocalDateTime fechaRegistro,
        Boolean activo
) {

    public static UserResponseDto from(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getNombre(),
                user.getEmail(),
                user.getNivel(),
                user.getAhorro(),
                user.getMeta(),
                user.getLeccionesCompletadas(),
                user.getRetosAceptados(),
                user.getFechaRegistro(),
                user.getActivo()
        );
    }
}
