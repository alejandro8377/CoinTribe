package com.cointribe.backend.controllers;

import com.cointribe.backend.dto.ChallengeDepositRequest;
import com.cointribe.backend.dto.ChallengeJoinRequest;
import com.cointribe.backend.dto.ChallengeResponseDto;
import com.cointribe.backend.dto.ChallengeRequest;
import com.cointribe.backend.dto.UserChallengeResponseDto;
import com.cointribe.backend.dto.ValidationGroups;
import com.cointribe.backend.models.Challenge;
import com.cointribe.backend.services.ChallengeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/challenges")
@CrossOrigin(origins = "*")
@Tag(name = "Retos", description = "Operaciones para consultar y administrar retos de ahorro")
public class ChallengeController {

    private final ChallengeService challengeService;

    @Autowired
    public ChallengeController(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }

    @GetMapping
    @Operation(summary = "Listar retos activos")
    @ApiResponse(responseCode = "200", description = "Lista de retos activos")
    public ResponseEntity<List<ChallengeResponseDto>> getActive() {
        return ResponseEntity.ok(challengeService.getActiveChallenges().stream()
                .map(ChallengeResponseDto::from)
                .toList());
    }

    @GetMapping("/all")
    @Operation(summary = "Listar todos los retos")
    @ApiResponse(responseCode = "200", description = "Lista de retos")
    public ResponseEntity<List<ChallengeResponseDto>> getAll() {
        return ResponseEntity.ok(challengeService.getAllChallenges().stream()
                .map(ChallengeResponseDto::from)
                .toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener reto")
    @ApiResponse(responseCode = "200", description = "Reto encontrado")
    public ResponseEntity<ChallengeResponseDto> getById(@PathVariable Long id) {
        return challengeService.getById(id)
                .map(ChallengeResponseDto::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{challengeId}/join")
    @Operation(summary = "Unirse a un reto")
    @ApiResponse(responseCode = "201", description = "Usuario unido al reto")
    public ResponseEntity<UserChallengeResponseDto> join(@PathVariable Long challengeId,
            @RequestBody @Valid ChallengeJoinRequest body) {
        Long userId = body.userId();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UserChallengeResponseDto.from(challengeService.joinChallenge(userId, challengeId)));
    }

    @PostMapping("/{challengeId}/deposit")
    @Operation(summary = "Registrar depósito en un reto")
    @ApiResponse(responseCode = "200", description = "Depósito registrado")
    public ResponseEntity<UserChallengeResponseDto> deposit(@PathVariable Long challengeId,
            @RequestBody @Valid ChallengeDepositRequest body) {
        return ResponseEntity.ok(UserChallengeResponseDto.from(
                challengeService.registerDeposit(body.userId(), challengeId, body.monto())));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Listar retos de un usuario")
    @ApiResponse(responseCode = "200", description = "Lista de retos del usuario")
    public ResponseEntity<List<UserChallengeResponseDto>> getUserChallenges(@PathVariable Long userId) {
        return ResponseEntity.ok(challengeService.getUserChallenges(userId).stream()
                .map(UserChallengeResponseDto::from)
                .toList());
    }

    /** POST /api/challenges — Crear reto */
    @PostMapping
    @Operation(summary = "Crear reto")
    @ApiResponse(responseCode = "201", description = "Reto creado")
    public ResponseEntity<ChallengeResponseDto> create(
            @RequestBody @Validated(ValidationGroups.Create.class) ChallengeRequest body) {
        Challenge challenge = challengeService.createChallenge(
                body.titulo(),
                body.descripcion(),
                body.metaAhorro(),
                body.duracionDias(),
                body.activo() != null ? body.activo() : true
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(ChallengeResponseDto.from(challenge));
    }

    /** PUT /api/challenges/{id} — Actualizar reto */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar reto")
    @ApiResponse(responseCode = "200", description = "Reto actualizado")
    public ResponseEntity<ChallengeResponseDto> update(@PathVariable Long id,
            @RequestBody @Validated(ValidationGroups.Update.class) ChallengeRequest body) {
        Challenge challenge = challengeService.updateChallenge(
                id,
                body.titulo(),
                body.descripcion(),
                body.metaAhorro(),
                body.duracionDias(),
                body.activo()
        );
        return ResponseEntity.ok(ChallengeResponseDto.from(challenge));
    }

    /** DELETE /api/challenges/{id} — Eliminar reto */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar reto")
    @ApiResponse(responseCode = "204", description = "Reto eliminado")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        challengeService.deleteChallenge(id);
        return ResponseEntity.noContent().build();
    }
}
