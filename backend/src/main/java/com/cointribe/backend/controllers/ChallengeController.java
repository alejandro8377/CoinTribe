package com.cointribe.backend.controllers;

import com.cointribe.backend.dto.ChallengeDepositRequest;
import com.cointribe.backend.dto.ChallengeJoinRequest;
import com.cointribe.backend.dto.ChallengeResponseDto;
import com.cointribe.backend.dto.ChallengeRequest;
import com.cointribe.backend.dto.UserChallengeResponseDto;
import com.cointribe.backend.models.Challenge;
import com.cointribe.backend.services.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/challenges")
@CrossOrigin(origins = "*")
public class ChallengeController {

    private final ChallengeService challengeService;

    @Autowired
    public ChallengeController(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }

    @GetMapping
    public ResponseEntity<List<ChallengeResponseDto>> getActive() {
        return ResponseEntity.ok(challengeService.getActiveChallenges().stream()
                .map(ChallengeResponseDto::from)
                .toList());
    }

    @GetMapping("/all")
    public ResponseEntity<List<ChallengeResponseDto>> getAll() {
        return ResponseEntity.ok(challengeService.getAllChallenges().stream()
                .map(ChallengeResponseDto::from)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChallengeResponseDto> getById(@PathVariable Long id) {
        return challengeService.getById(id)
                .map(ChallengeResponseDto::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{challengeId}/join")
    public ResponseEntity<UserChallengeResponseDto> join(@PathVariable Long challengeId,
                                                          @RequestBody ChallengeJoinRequest body) {
        Long userId = body.userId();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UserChallengeResponseDto.from(challengeService.joinChallenge(userId, challengeId)));
    }

    @PostMapping("/{challengeId}/deposit")
    public ResponseEntity<UserChallengeResponseDto> deposit(@PathVariable Long challengeId,
                                                             @RequestBody ChallengeDepositRequest body) {
        return ResponseEntity.ok(UserChallengeResponseDto.from(
                challengeService.registerDeposit(body.userId(), challengeId, body.monto())));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserChallengeResponseDto>> getUserChallenges(@PathVariable Long userId) {
        return ResponseEntity.ok(challengeService.getUserChallenges(userId).stream()
                .map(UserChallengeResponseDto::from)
                .toList());
    }

    /** POST /api/challenges — Crear reto */
    @PostMapping
    public ResponseEntity<ChallengeResponseDto> create(@RequestBody ChallengeRequest body) {
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
    public ResponseEntity<ChallengeResponseDto> update(@PathVariable Long id,
                                                       @RequestBody ChallengeRequest body) {
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
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        challengeService.deleteChallenge(id);
        return ResponseEntity.noContent().build();
    }
}
