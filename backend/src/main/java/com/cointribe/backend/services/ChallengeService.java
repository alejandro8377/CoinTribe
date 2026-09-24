package com.cointribe.backend.services;

import com.cointribe.backend.exceptions.BusinessException;
import com.cointribe.backend.models.Challenge;
import com.cointribe.backend.models.User;
import com.cointribe.backend.models.UserChallenge;
import com.cointribe.backend.repositories.ChallengeRepository;
import com.cointribe.backend.repositories.UserChallengeRepository;
import com.cointribe.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final UserChallengeRepository userChallengeRepository;
    private final UserRepository userRepository;
    private final AchievementService achievementService;

    @Autowired
    public ChallengeService(ChallengeRepository challengeRepository,
                            UserChallengeRepository userChallengeRepository,
                            UserRepository userRepository,
                            AchievementService achievementService) {
        this.challengeRepository = challengeRepository;
        this.userChallengeRepository = userChallengeRepository;
        this.userRepository = userRepository;
        this.achievementService = achievementService;
    }

    public List<Challenge> getActiveChallenges() {
        return challengeRepository.findByActivoTrue();
    }

    public List<Challenge> getAllChallenges() {
        return challengeRepository.findAll();
    }

    public Optional<Challenge> getById(Long id) {
        return challengeRepository.findById(id);
    }

    /**
     * REGLA DE NEGOCIO 3: Un usuario no puede unirse dos veces al mismo reto.
     * Si ya existe la relación UserChallenge, se rechaza la operación.
     */
    @Transactional
    public UserChallenge joinChallenge(Long userId, Long challengeId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("Usuario no encontrado."));
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new BusinessException("Reto no encontrado."));

        if (!Boolean.TRUE.equals(challenge.getActivo())) {
            throw new BusinessException("Este reto ya no está activo.");
        }
        if (userChallengeRepository.existsByUserIdAndChallengeId(userId, challengeId)) {
            throw new BusinessException("Ya te uniste a este reto. No puedes unirte dos veces.");
        }

        UserChallenge uc = new UserChallenge();
        uc.setUser(user);
        uc.setChallenge(challenge);
        uc.setAhorroAcumulado(0.0);
        uc.setCompletado(false);

        UserChallenge saved = userChallengeRepository.save(uc);

        // Actualizar contador de retos del usuario
        user.setRetosAceptados(user.getRetosAceptados() + 1);
        userRepository.save(user);
        achievementService.evaluateAndUnlock(user);

        return saved;
    }

    /**
     * REGLA DE NEGOCIO 4: El depósito en un reto debe ser mayor a cero
     * y no puede hacer que el ahorro acumulado del reto supere la meta del reto.
     */
    @Transactional
    public UserChallenge registerDeposit(Long userId, Long challengeId, Double monto) {
        if (monto == null || monto <= 0) {
            throw new BusinessException("El monto del depósito debe ser mayor a cero.");
        }

        UserChallenge uc = userChallengeRepository.findByUserIdAndChallengeId(userId, challengeId)
                .orElseThrow(() -> new BusinessException(
                        "No estás unido a este reto. Únete primero antes de depositar."));

        if (Boolean.TRUE.equals(uc.getCompletado())) {
            throw new BusinessException("Este reto ya está completado. No se aceptan más depósitos.");
        }

        Double meta = uc.getChallenge().getMetaAhorro();
        Double nuevoAhorro = uc.getAhorroAcumulado() + monto;

        if (meta != null && nuevoAhorro > meta) {
            throw new BusinessException(
                    "El depósito supera la meta del reto. Máximo permitido restante: "
                            + (meta - uc.getAhorroAcumulado()));
        }

        uc.setAhorroAcumulado(nuevoAhorro);
        if (meta != null && nuevoAhorro >= meta) {
            uc.setCompletado(true);
        }

        // También sumar al ahorro global del usuario
        User user = uc.getUser();
        user.setAhorro(user.getAhorro() + monto);
        userRepository.save(user);

        UserChallenge saved = userChallengeRepository.save(uc);
        achievementService.evaluateAndUnlock(user);
        return saved;
    }

    public List<UserChallenge> getUserChallenges(Long userId) {
        return userChallengeRepository.findByUserId(userId);
    }

    public Challenge saveChallenge(Challenge challenge) {
        return challengeRepository.save(challenge);
    }
}
