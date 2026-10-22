package com.cointribe.backend.controllers;

import com.cointribe.backend.models.Achievement;
import com.cointribe.backend.models.UserAchievement;
import com.cointribe.backend.services.AchievementService;
import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
=======
import org.springframework.http.HttpStatus;
>>>>>>> e5646a2 (Nuevos endpoints e implementacion de base de datos en NEON)
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
<<<<<<< HEAD
=======
import java.util.Map;
>>>>>>> e5646a2 (Nuevos endpoints e implementacion de base de datos en NEON)

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
    public ResponseEntity<List<Achievement>> getAll() {
        return ResponseEntity.ok(achievementService.getAllAchievements());
    }

<<<<<<< HEAD
=======
    @GetMapping("/{id}")
    public ResponseEntity<Achievement> getById(@PathVariable Long id) {
        return achievementService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

>>>>>>> e5646a2 (Nuevos endpoints e implementacion de base de datos en NEON)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserAchievement>> getUserAchievements(@PathVariable Long userId) {
        return ResponseEntity.ok(achievementService.getUserAchievements(userId));
    }
<<<<<<< HEAD
=======

    /** POST /api/achievements — Crear logro */
    @PostMapping
    public ResponseEntity<Achievement> create(@RequestBody Map<String, Object> body) {
        String codigo = body.get("codigo") != null ? body.get("codigo").toString() : null;
        String icono = body.get("icono") != null ? body.get("icono").toString() : null;
        String nombre = body.get("nombre") != null ? body.get("nombre").toString() : null;
        String pista = body.get("pista") != null ? body.get("pista").toString() : null;
        Double ahorroMinimo = body.get("ahorroMinimo") != null
                ? Double.valueOf(body.get("ahorroMinimo").toString()) : null;
        Integer leccionesMinimas = body.get("leccionesMinimas") != null
                ? Integer.valueOf(body.get("leccionesMinimas").toString()) : null;
        Integer retosMinimos = body.get("retosMinimos") != null
                ? Integer.valueOf(body.get("retosMinimos").toString()) : null;
        Achievement ach = achievementService.createAchievement(
                codigo, icono, nombre, pista, ahorroMinimo, leccionesMinimas, retosMinimos);
        return ResponseEntity.status(HttpStatus.CREATED).body(ach);
    }

    /** PUT /api/achievements/{id} — Actualizar logro */
    @PutMapping("/{id}")
    public ResponseEntity<Achievement> update(@PathVariable Long id,
                                              @RequestBody Map<String, Object> body) {
        String codigo = body.get("codigo") != null ? body.get("codigo").toString() : null;
        String icono = body.get("icono") != null ? body.get("icono").toString() : null;
        String nombre = body.get("nombre") != null ? body.get("nombre").toString() : null;
        String pista = body.get("pista") != null ? body.get("pista").toString() : null;
        Double ahorroMinimo = body.get("ahorroMinimo") != null
                ? Double.valueOf(body.get("ahorroMinimo").toString()) : null;
        Integer leccionesMinimas = body.get("leccionesMinimas") != null
                ? Integer.valueOf(body.get("leccionesMinimas").toString()) : null;
        Integer retosMinimos = body.get("retosMinimos") != null
                ? Integer.valueOf(body.get("retosMinimos").toString()) : null;
        Achievement ach = achievementService.updateAchievement(
                id, codigo, icono, nombre, pista, ahorroMinimo, leccionesMinimas, retosMinimos);
        return ResponseEntity.ok(ach);
    }

    /** DELETE /api/achievements/{id} — Eliminar logro */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        achievementService.deleteAchievement(id);
        return ResponseEntity.noContent().build();
    }
>>>>>>> e5646a2 (Nuevos endpoints e implementacion de base de datos en NEON)
}
