package com.cointribe.backend.controllers;

import com.cointribe.backend.dto.AchievementRequest;
import com.cointribe.backend.dto.AchievementResponseDto;
import com.cointribe.backend.dto.UserAchievementResponseDto;
import com.cointribe.backend.models.Achievement;
import com.cointribe.backend.services.AchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/achievements")
@CrossOrigin(origins = "*")
public class AchievementController {

    private final AchievementService achievementService;

    @Autowired
    public AchievementController(AchievementService achievementService) {
        this.achievementService = achievementService;
    }

    @GetMapping
    public ResponseEntity<List<AchievementResponseDto>> getAll() {
        return ResponseEntity.ok(achievementService.getAllAchievements().stream()
                .map(AchievementResponseDto::from)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AchievementResponseDto> getById(@PathVariable Long id) {
        return achievementService.getById(id)
                .map(AchievementResponseDto::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserAchievementResponseDto>> getUserAchievements(@PathVariable Long userId) {
        return ResponseEntity.ok(achievementService.getUserAchievements(userId).stream()
                .map(UserAchievementResponseDto::from)
                .toList());
    }

    /** POST /api/achievements — Crear logro */
    @PostMapping
    public ResponseEntity<AchievementResponseDto> create(@RequestBody AchievementRequest body) {
        Achievement ach = achievementService.createAchievement(
                body.codigo(),
                body.icono(),
                body.nombre(),
                body.pista(),
                body.ahorroMinimo(),
                body.leccionesMinimas(),
                body.retosMinimos()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(AchievementResponseDto.from(ach));
    }

    /** PUT /api/achievements/{id} — Actualizar logro */
    @PutMapping("/{id}")
    public ResponseEntity<AchievementResponseDto> update(@PathVariable Long id,
                                                          @RequestBody AchievementRequest body) {
        Achievement ach = achievementService.updateAchievement(
                id,
                body.codigo(),
                body.icono(),
                body.nombre(),
                body.pista(),
                body.ahorroMinimo(),
                body.leccionesMinimas(),
                body.retosMinimos()
        );
        return ResponseEntity.ok(AchievementResponseDto.from(ach));
    }

    /** DELETE /api/achievements/{id} — Eliminar logro */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        achievementService.deleteAchievement(id);
        return ResponseEntity.noContent().build();
    }
}
