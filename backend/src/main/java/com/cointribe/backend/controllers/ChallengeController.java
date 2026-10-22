package com.cointribe.backend.controllers;

import com.cointribe.backend.models.Challenge;
import com.cointribe.backend.models.UserChallenge;
import com.cointribe.backend.services.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<List<Challenge>> getActive() {
        return ResponseEntity.ok(challengeService.getActiveChallenges());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Challenge>> getAll() {
        return ResponseEntity.ok(challengeService.getAllChallenges());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Challenge> getById(@PathVariable Long id) {
        return challengeService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{challengeId}/join")
    public ResponseEntity<UserChallenge> join(@PathVariable Long challengeId,
                                              @RequestBody Map<String, Long> body) {
        Long userId = body.get("userId");
        UserChallenge uc = challengeService.joinChallenge(userId, challengeId);
        return ResponseEntity.status(HttpStatus.CREATED).body(uc);
    }

    @PostMapping("/{challengeId}/deposit")
    public ResponseEntity<UserChallenge> deposit(@PathVariable Long challengeId,
                                                 @RequestBody Map<String, Object> body) {
        Long userId = Long.valueOf(body.get("userId").toString());
        Double monto = Double.valueOf(body.get("monto").toString());
        UserChallenge uc = challengeService.registerDeposit(userId, challengeId, monto);
        return ResponseEntity.ok(uc);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserChallenge>> getUserChallenges(@PathVariable Long userId) {
        return ResponseEntity.ok(challengeService.getUserChallenges(userId));
    }

    /** POST /api/challenges — Crear reto */
    @PostMapping
    public ResponseEntity<Challenge> create(@RequestBody Map<String, Object> body) {
        String titulo = body.get("titulo") != null ? body.get("titulo").toString() : null;
        String descripcion = body.get("descripcion") != null ? body.get("descripcion").toString() : null;
        Double metaAhorro = body.get("metaAhorro") != null ? Double.valueOf(body.get("metaAhorro").toString()) : null;
        Integer duracionDias = body.get("duracionDias") != null ? Integer.valueOf(body.get("duracionDias").toString()) : null;
        Boolean activo = body.get("activo") != null ? Boolean.valueOf(body.get("activo").toString()) : true;
        Challenge challenge = challengeService.createChallenge(titulo, descripcion, metaAhorro, duracionDias, activo);
        return ResponseEntity.status(HttpStatus.CREATED).body(challenge);
    }

    /** PUT /api/challenges/{id} — Actualizar reto */
    @PutMapping("/{id}")
    public ResponseEntity<Challenge> update(@PathVariable Long id,
                                            @RequestBody Map<String, Object> body) {
        String titulo = body.get("titulo") != null ? body.get("titulo").toString() : null;
        String descripcion = body.get("descripcion") != null ? body.get("descripcion").toString() : null;
        Double metaAhorro = body.get("metaAhorro") != null ? Double.valueOf(body.get("metaAhorro").toString()) : null;
        Integer duracionDias = body.get("duracionDias") != null ? Integer.valueOf(body.get("duracionDias").toString()) : null;
        Boolean activo = body.get("activo") != null ? Boolean.valueOf(body.get("activo").toString()) : null;
        Challenge challenge = challengeService.updateChallenge(id, titulo, descripcion, metaAhorro, duracionDias, activo);
        return ResponseEntity.ok(challenge);
    }

    /** DELETE /api/challenges/{id} — Eliminar reto */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        challengeService.deleteChallenge(id);
        return ResponseEntity.noContent().build();
    }
}
