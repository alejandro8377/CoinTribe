package com.cointribe.backend.controllers;

import com.cointribe.backend.dto.AddSavingsRequest;
import com.cointribe.backend.dto.ChangePasswordRequest;
import com.cointribe.backend.dto.UpdateProfileRequest;
import com.cointribe.backend.dto.UpdateUserRequest;
import com.cointribe.backend.dto.UserLoginRequest;
import com.cointribe.backend.dto.UserRegistrationRequest;
import com.cointribe.backend.dto.UserResponseDto;
import com.cointribe.backend.models.User;
import com.cointribe.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<UserResponseDto> register(@RequestBody UserRegistrationRequest body) {
        User user = userService.registerUser(
                body.nombre(),
                body.email(),
                body.password()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponseDto.from(user));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody UserLoginRequest body) {
        User user = userService.login(body.email(), body.password());
        return ResponseEntity.ok(UserResponseDto.from(user));
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<UserResponseDto> changePassword(@PathVariable Long id,
                                                          @RequestBody ChangePasswordRequest body) {
        User user = userService.changePassword(
                id,
                body.currentPassword(),
                body.newPassword()
        );
        return ResponseEntity.ok(UserResponseDto.from(user));
    }

    @PutMapping("/{id}/profile")
    public ResponseEntity<UserResponseDto> updateProfile(@PathVariable Long id,
                                                          @RequestBody UpdateProfileRequest body) {
        User user = userService.updateProfile(id, body.nombre(), body.meta());
        return ResponseEntity.ok(UserResponseDto.from(user));
    }

    @PostMapping("/{id}/ahorro")
    public ResponseEntity<UserResponseDto> addAhorro(@PathVariable Long id,
                                                     @RequestBody AddSavingsRequest body) {
        User user = userService.addAhorro(id, body.monto());
        return ResponseEntity.ok(UserResponseDto.from(user));
    }

    /** PUT /api/users/{id} — Actualización completa del usuario */
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable Long id,
                                                  @RequestBody UpdateUserRequest body) {
        User user = userService.updateUser(id, body.nombre(), body.email(), body.meta(), body.nivel());
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
