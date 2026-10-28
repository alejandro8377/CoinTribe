package com.cointribe.backend.controllers;

import com.cointribe.backend.dto.UserResponseDto;
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
    public ResponseEntity<List<UserResponseDto>> getAll() {
        List<UserResponseDto> users = userService.getAllUsers().stream()
                .map(UserResponseDto::from)
                .toList();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(UserResponseDto::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody Map<String, String> body) {
        User user = userService.registerUser(
                body.get("nombre"),
                body.get("email"),
                body.get("password")
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponseDto.from(user));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody Map<String, String> body) {
        User user = userService.login(body.get("email"), body.get("password"));
        return ResponseEntity.ok(UserResponseDto.from(user));
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<UserResponseDto> changePassword(@PathVariable Long id,
                                                          @RequestBody Map<String, String> body) {
        User user = userService.changePassword(
                id,
                body.get("currentPassword"),
                body.get("newPassword")
        );
        return ResponseEntity.ok(UserResponseDto.from(user));
    }

    @PutMapping("/{id}/profile")
    public ResponseEntity<UserResponseDto> updateProfile(@PathVariable Long id,
                                                          @RequestBody Map<String, Object> body) {
        String nombre = body.get("nombre") != null ? body.get("nombre").toString() : null;
        Double meta = body.get("meta") != null ? Double.valueOf(body.get("meta").toString()) : null;
        User user = userService.updateProfile(id, nombre, meta);
        return ResponseEntity.ok(UserResponseDto.from(user));
    }

    @PostMapping("/{id}/ahorro")
    public ResponseEntity<UserResponseDto> addAhorro(@PathVariable Long id,
                                                     @RequestBody Map<String, Double> body) {
        User user = userService.addAhorro(id, body.get("monto"));
        return ResponseEntity.ok(UserResponseDto.from(user));
    }

    /** PUT /api/users/{id} — Actualización completa del usuario */
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable Long id,
                                                  @RequestBody Map<String, Object> body) {
        String nombre = body.get("nombre") != null ? body.get("nombre").toString() : null;
        String email = body.get("email") != null ? body.get("email").toString() : null;
        Double meta = body.get("meta") != null ? Double.valueOf(body.get("meta").toString()) : null;
        Integer nivel = body.get("nivel") != null ? Integer.valueOf(body.get("nivel").toString()) : null;
        User user = userService.updateUser(id, nombre, email, meta, nivel);
        return ResponseEntity.ok(UserResponseDto.from(user));
    }

    /** DELETE /api/users/{id} — Eliminación física */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    /** PUT /api/users/{id}/deactivate — Soft-delete (desactivar cuenta) */
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<UserResponseDto> deactivate(@PathVariable Long id) {
        User user = userService.deactivateUser(id);
        return ResponseEntity.ok(UserResponseDto.from(user));
    }

    /** PUT /api/users/{id}/activate — Reactivar cuenta */
    @PutMapping("/{id}/activate")
    public ResponseEntity<UserResponseDto> activate(@PathVariable Long id) {
        User user = userService.activateUser(id);
        return ResponseEntity.ok(UserResponseDto.from(user));
    }
}
