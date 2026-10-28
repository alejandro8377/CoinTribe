package com.cointribe.backend.dto;

import com.cointribe.backend.models.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class UserResponseDtoTest {

    @Test
    void fromCopiesPublicUserFieldsAndExcludesPassword() {
        LocalDateTime fechaRegistro = LocalDateTime.of(2026, 10, 28, 12, 0);
        User user = new User(
                7L,
                "Alejandro",
                "alejandro@example.com",
                "secret-password",
                2,
                1250.0,
                5000.0,
                3,
                4,
                fechaRegistro,
                true
        );

        UserResponseDto dto = UserResponseDto.from(user);

        assertEquals(7L, dto.id());
        assertEquals("Alejandro", dto.nombre());
        assertEquals("alejandro@example.com", dto.email());
        assertEquals(2, dto.nivel());
        assertEquals(1250.0, dto.ahorro());
        assertEquals(5000.0, dto.meta());
        assertEquals(3, dto.leccionesCompletadas());
        assertEquals(4, dto.retosAceptados());
        assertEquals(fechaRegistro, dto.fechaRegistro());
        assertEquals(true, dto.activo());
        assertFalse(Arrays.stream(UserResponseDto.class.getRecordComponents())
                .anyMatch(component -> component.getName().equals("password")));
    }
}
