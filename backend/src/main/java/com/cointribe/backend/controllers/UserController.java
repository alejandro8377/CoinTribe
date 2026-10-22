package com.cointribe.backend.controllers;

import com.cointribe.backend.models.User;
import com.cointribe.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody Map<String, String> body) {
        User user = userService.registerUser(
                body.get("nombre"),
                body.get("email"),
                body.get("password")
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody Map<String, String> body) {
        User user = userService.login(body.get("email"), body.get("password"));
        // En producción no se devolvería el password
        user.setPassword(null);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<User> changePassword(@PathVariable Long id,
                                               @RequestBody Map<String, String> body) {
        User user = userService.changePassword(
                id,
                body.get("currentPassword"),
                body.get("newPassword")
        );
        user.setPassword(null);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}/profile")
    public ResponseEntity<User> updateProfile(@PathVariable Long id,
                                              @RequestBody Map<String, Object> body) {
        String nombre = body.get("nombre") != null ? body.get("nombre").toString() : null;
        Double meta = body.get("meta") != null ? Double.valueOf(body.get("meta").toString()) : null;
        User user = userService.updateProfile(id, nombre, meta);
        user.setPassword(null);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/{id}/ahorro")
    public ResponseEntity<User> addAhorro(@PathVariable Long id,
                                          @RequestBody Map<String, Double> body) {
        User user = userService.addAhorro(id, body.get("monto"));
        user.setPassword(null);
        return ResponseEntity.ok(user);
    }
<<<<<<< HEAD
=======

    /** PUT /api/users/{id} — Actualización completa del usuario */
    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id,
                                       @RequestBody Map<String, Object> body) {
        String nombre = body.get("nombre") != null ? body.get("nombre").toString() : null;
        String email = body.get("email") != null ? body.get("email").toString() : null;
        Double meta = body.get("meta") != null ? Double.valueOf(body.get("meta").toString()) : null;
        Integer nivel = body.get("nivel") != null ? Integer.valueOf(body.get("nivel").toString()) : null;
        User user = userService.updateUser(id, nombre, email, meta, nivel);
        user.setPassword(null);
        return ResponseEntity.ok(user);
    }

    /** DELETE /api/users/{id} — Eliminación física */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    /** PUT /api/users/{id}/deactivate — Soft-delete (desactivar cuenta) */
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<User> deactivate(@PathVariable Long id) {
        User user = userService.deactivateUser(id);
        user.setPassword(null);
        return ResponseEntity.ok(user);
    }

    /** PUT /api/users/{id}/activate — Reactivar cuenta */
    @PutMapping("/{id}/activate")
    public ResponseEntity<User> activate(@PathVariable Long id) {
        User user = userService.activateUser(id);
        user.setPassword(null);
        return ResponseEntity.ok(user);
    }
>>>>>>> e5646a2 (Nuevos endpoints e implementacion de base de datos en NEON)
}
