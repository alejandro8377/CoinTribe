package com.cointribe.backend.services;

import com.cointribe.backend.exceptions.BusinessException;
import com.cointribe.backend.models.User;
import com.cointribe.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService {

    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private final UserRepository userRepository;
    private final AchievementService achievementService;

    @Autowired
    public UserService(UserRepository userRepository, AchievementService achievementService) {
        this.userRepository = userRepository;
        this.achievementService = achievementService;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * REGLA DE NEGOCIO 1: El correo electrónico debe ser único en el sistema.
     * No se permite registrar dos cuentas con el mismo email.
     *
     * REGLA DE NEGOCIO 2: La contraseña debe tener al menos 6 caracteres
     * y el email debe tener un formato válido.
     */
    @Transactional
    public User registerUser(String nombre, String email, String password) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new BusinessException("El nombre es obligatorio.");
        }
        if (email == null || !EMAIL_PATTERN.matcher(email.trim()).matches()) {
            throw new BusinessException("El correo electrónico no tiene un formato válido.");
        }
        String emailNorm = email.trim().toLowerCase();
        if (userRepository.existsByEmail(emailNorm)) {
            throw new BusinessException("Ya existe una cuenta registrada con ese correo electrónico.");
        }
        if (password == null || password.length() < MIN_PASSWORD_LENGTH) {
            throw new BusinessException(
                    "La contraseña debe tener al menos " + MIN_PASSWORD_LENGTH + " caracteres.");
        }

        User user = new User();
        user.setNombre(nombre.trim());
        user.setEmail(emailNorm);
        user.setPassword(password); // En producción se debería hashear (BCrypt)
        user.setNivel(1);
        user.setAhorro(0.0);
        user.setMeta(500000.0);
        user.setLeccionesCompletadas(0);
        user.setRetosAceptados(0);
        user.setActivo(true);

        User saved = userRepository.save(user);
        achievementService.evaluateAndUnlock(saved);
        return saved;
    }

    /**
     * Login simple (sin hash por simplicidad del proyecto académico).
     */
    public User login(String email, String password) {
        if (email == null || password == null) {
            throw new BusinessException("Correo y contraseña son obligatorios.");
        }
        User user = userRepository.findByEmail(email.trim().toLowerCase())
                .orElseThrow(() -> new BusinessException("Credenciales incorrectas."));
        if (!user.getPassword().equals(password)) {
            throw new BusinessException("Credenciales incorrectas.");
        }
        if (Boolean.FALSE.equals(user.getActivo())) {
            throw new BusinessException("La cuenta está desactivada.");
        }
        return user;
    }

    /**
     * REGLA DE NEGOCIO 2 (continuación): Al cambiar contraseña también se exige
     * longitud mínima y que la nueva sea distinta de la actual.
     */
    @Transactional
    public User changePassword(Long userId, String currentPassword, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("Usuario no encontrado."));
        if (!user.getPassword().equals(currentPassword)) {
            throw new BusinessException("La contraseña actual no es correcta.");
        }
        if (newPassword == null || newPassword.length() < MIN_PASSWORD_LENGTH) {
            throw new BusinessException(
                    "La nueva contraseña debe tener al menos " + MIN_PASSWORD_LENGTH + " caracteres.");
        }
        if (newPassword.equals(currentPassword)) {
            throw new BusinessException("La nueva contraseña debe ser distinta de la actual.");
        }
        user.setPassword(newPassword);
        return userRepository.save(user);
    }

    @Transactional
    public User updateProfile(Long userId, String nombre, Double meta) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("Usuario no encontrado."));
        if (nombre != null && !nombre.trim().isEmpty()) {
            user.setNombre(nombre.trim());
        }
        if (meta != null) {
            if (meta <= 0) {
                throw new BusinessException("La meta de ahorro debe ser mayor a cero.");
            }
            user.setMeta(meta);
        }
        return userRepository.save(user);
    }

    @Transactional
    public User addAhorro(Long userId, Double monto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("Usuario no encontrado."));
        if (monto == null || monto <= 0) {
            throw new BusinessException("El monto a depositar debe ser mayor a cero.");
        }
        user.setAhorro(user.getAhorro() + monto);
        User saved = userRepository.save(user);
        achievementService.evaluateAndUnlock(saved);
        return saved;
    }
}
