package com.cointribe.backend.dto;

import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RequestDtoTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void mapsExistingJsonPropertiesToTypedRequestDtos() throws Exception {
        UserRegistrationRequest registration = objectMapper.readValue(
                """
                        {"nombre":"Ana Pérez","email":"ana@example.com","password":"123456"}
                        """,
                UserRegistrationRequest.class
        );
        assertEquals("Ana Pérez", registration.nombre());
        assertEquals("ana@example.com", registration.email());
        assertEquals("123456", registration.password());

        CourseUpdateRequest course = objectMapper.readValue(
                """
                        {"titulo":"Ahorro","tema":"Finanzas","descripcion":"Curso básico","totalLecciones":6,"progreso":25}
                        """,
                CourseUpdateRequest.class
        );
        assertEquals(6, course.totalLecciones());
        assertEquals(25, course.progreso());

        ChallengeDepositRequest deposit = objectMapper.readValue(
                """
                        {"userId":7,"monto":10000}
                        """,
                ChallengeDepositRequest.class
        );
        assertEquals(7L, deposit.userId());
        assertEquals(10000.0, deposit.monto());
    }

    @Test
    void permitsOmittedOptionalFieldsInUpdateRequests() throws Exception {
        UpdateProfileRequest profile = objectMapper.readValue(
                """
                        {"nombre":"Ana"}
                        """,
                UpdateProfileRequest.class
        );
        assertEquals("Ana", profile.nombre());
        assertNull(profile.meta());

        ChallengeRequest challenge = objectMapper.readValue(
                """
                        {"titulo":"Ahorro semanal"}
                        """,
                ChallengeRequest.class
        );
        assertNull(challenge.activo());
        assertNull(challenge.metaAhorro());
    }
}
