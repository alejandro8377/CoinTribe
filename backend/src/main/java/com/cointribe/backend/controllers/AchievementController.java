package com.cointribe.backend.controllers;

import com.cointribe.backend.dto.AchievementRequest;
import com.cointribe.backend.dto.AchievementResponseDto;
import com.cointribe.backend.dto.UserAchievementResponseDto;
import com.cointribe.backend.dto.ValidationGroups;
import com.cointribe.backend.models.Achievement;
import com.cointribe.backend.services.AchievementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/achievements")
@CrossOrigin(origins = "*")
@Tag(name = "Logros", description = "Operaciones para consultar y administrar logros")
public class AchievementController {

    private final AchievementService achievementService;

    @Autowired
    public AchievementController(AchievementService achievementService) {
        this.achievementService = achievementService;
    }

    @GetMapping
    @Operation(summary = "Listar logros")
    @ApiResponse(responseCode = "200", description = "Lista de logros")
    public ResponseEntity<List<AchievementResponseDto>> getAll() {
        return ResponseEntity.ok(achievementService.getAllAchievements().stream()
                .map(AchievementResponseDto::from)
                .toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener logro")
    @ApiResponse(responseCode = "200", description = "Logro encontrado")
    public ResponseEntity<AchievementResponseDto> getById(@PathVariable Long id) {
        return achievementService.getById(id)
                .map(AchievementResponseDto::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Listar logros de un usuario")
    @ApiResponse(responseCode = "200", description = "Lista de logros del usuario")
    public ResponseEntity<List<UserAchievementResponseDto>> getUserAchievements(@PathVariable Long userId) {
        return ResponseEntity.ok(achievementService.getUserAchievements(userId).stream()
                .map(UserAchievementResponseDto::from)
                .toList());
    }

    /** POST /api/achievements — Crear logro */
    @PostMapping
    @Operation(summary = "Crear logro")
    @ApiResponse(responseCode = "201", description = "Logro creado")
    public ResponseEntity<AchievementResponseDto> create(
            @RequestBody @Validated(ValidationGroups.Create.class) AchievementRequest body) {
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
    @Operation(summary = "Actualizar logro")
    @ApiResponse(responseCode = "200", description = "Logro actualizado")
    public ResponseEntity<AchievementResponseDto> update(@PathVariable Long id,
            @RequestBody @Validated(ValidationGroups.Update.class) AchievementRequest body) {
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
    @Operation(summary = "Eliminar logro")
    @ApiResponse(responseCode = "204", description = "Logro eliminado")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        achievementService.deleteAchievement(id);
        return ResponseEntity.noContent().build();
    }
}
