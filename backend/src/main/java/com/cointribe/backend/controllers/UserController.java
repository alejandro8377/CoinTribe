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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
@Tag(name = "Usuarios", description = "Operaciones para registrar y administrar usuarios")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "Listar usuarios")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios")
    public ResponseEntity<List<UserResponseDto>> getAll() {
        List<UserResponseDto> users = userService.getAllUsers().stream()
                .map(UserResponseDto::from)
                .toList();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario")
    @ApiResponse(responseCode = "200", description = "Usuario encontrado")
    public ResponseEntity<UserResponseDto> getById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(UserResponseDto::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar usuario")
    @ApiResponse(responseCode = "201", description = "Usuario registrado")
    public ResponseEntity<UserResponseDto> register(@RequestBody @Valid UserRegistrationRequest body) {
        User user = userService.registerUser(
                body.nombre(),
                body.email(),
                body.password()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponseDto.from(user));
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión")
    @ApiResponse(responseCode = "200", description = "Inicio de sesión correcto")
    public ResponseEntity<UserResponseDto> login(@RequestBody @Valid UserLoginRequest body) {
        User user = userService.login(body.email(), body.password());
        return ResponseEntity.ok(UserResponseDto.from(user));
    }

    @PutMapping("/{id}/password")
    @Operation(summary = "Cambiar contraseña")
    @ApiResponse(responseCode = "200", description = "Contraseña actualizada")
    public ResponseEntity<UserResponseDto> changePassword(@PathVariable Long id,
            @RequestBody @Valid ChangePasswordRequest body) {
        User user = userService.changePassword(
                id,
                body.currentPassword(),
                body.newPassword()
        );
        return ResponseEntity.ok(UserResponseDto.from(user));
    }

    @PutMapping("/{id}/profile")
    @Operation(summary = "Actualizar perfil")
    @ApiResponse(responseCode = "200", description = "Perfil actualizado")
    public ResponseEntity<UserResponseDto> updateProfile(@PathVariable Long id,
            @RequestBody @Valid UpdateProfileRequest body) {
        User user = userService.updateProfile(id, body.nombre(), body.meta());
        return ResponseEntity.ok(UserResponseDto.from(user));
    }

    @PostMapping("/{id}/ahorro")
    @Operation(summary = "Registrar ahorro")
    @ApiResponse(responseCode = "200", description = "Ahorro actualizado")
    public ResponseEntity<UserResponseDto> addAhorro(@PathVariable Long id,
            @RequestBody @Valid AddSavingsRequest body) {
        User user = userService.addAhorro(id, body.monto());
        return ResponseEntity.ok(UserResponseDto.from(user));
    }

    /** PUT /api/users/{id} — Actualización completa del usuario */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario")
    @ApiResponse(responseCode = "200", description = "Usuario actualizado")
    public ResponseEntity<UserResponseDto> update(@PathVariable Long id,
            @RequestBody @Valid UpdateUserRequest body) {
        User user = userService.updateUser(id, body.nombre(), body.email(), body.meta(), body.nivel());
        return ResponseEntity.ok(UserResponseDto.from(user));
    }

    /** DELETE /api/users/{id} — Eliminación física */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario")
    @ApiResponse(responseCode = "204", description = "Usuario eliminado")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    /** PUT /api/users/{id}/deactivate — Soft-delete (desactivar cuenta) */
    @PutMapping("/{id}/deactivate")
    @Operation(summary = "Desactivar usuario")
    @ApiResponse(responseCode = "200", description = "Usuario desactivado")
    public ResponseEntity<UserResponseDto> deactivate(@PathVariable Long id) {
        User user = userService.deactivateUser(id);
        return ResponseEntity.ok(UserResponseDto.from(user));
    }

    /** PUT /api/users/{id}/activate — Reactivar cuenta */
    @PutMapping("/{id}/activate")
    @Operation(summary = "Reactivar usuario")
    @ApiResponse(responseCode = "200", description = "Usuario reactivado")
    public ResponseEntity<UserResponseDto> activate(@PathVariable Long id) {
        User user = userService.activateUser(id);
        return ResponseEntity.ok(UserResponseDto.from(user));
    }
}
