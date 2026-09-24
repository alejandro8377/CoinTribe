package com.cointribe.backend.services;

import com.cointribe.backend.models.Achievement;
import com.cointribe.backend.models.User;
import com.cointribe.backend.models.UserAchievement;
import com.cointribe.backend.repositories.AchievementRepository;
import com.cointribe.backend.repositories.UserAchievementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servicio de logros. Evalúa automáticamente si el usuario cumple
 * las condiciones de cada logro y lo desbloquea.
 */
@Service
public class AchievementService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AchievementService.class);

    private final AchievementRepository achievementRepository;
    private final UserAchievementRepository userAchievementRepository;

    @Autowired
    public AchievementService(AchievementRepository achievementRepository,
                              UserAchievementRepository userAchievementRepository) {
        this.achievementRepository = achievementRepository;
        this.userAchievementRepository = userAchievementRepository;
    }

    public List<Achievement> getAllAchievements() {
        return achievementRepository.findAll();
    }

    public List<UserAchievement> getUserAchievements(Long userId) {
        return userAchievementRepository.findByUserId(userId);
    }

    /**
     * Evalúa todos los logros disponibles y desbloquea los que el usuario cumpla
     * y aún no tenga. Se invoca después de depósitos, lecciones o uniones a retos.
     */
    @Transactional
    public void evaluateAndUnlock(User user) {
        if (user == null || user.getId() == null) return;

        List<Achievement> all = achievementRepository.findAll();
        for (Achievement ach : all) {
            if (userAchievementRepository.existsByUserIdAndAchievementId(user.getId(), ach.getId())) {
                continue; // ya lo tiene
            }
            if (meetsConditions(user, ach)) {
                UserAchievement ua = new UserAchievement();
                ua.setUser(user);
                ua.setAchievement(ach);
                userAchievementRepository.save(ua);
                LOGGER.info("Logro desbloqueado: userId={}, achievementId={}", user.getId(), ach.getId());
            }
        }
    }

    private boolean meetsConditions(User user, Achievement ach) {
        boolean ok = true;
        if (ach.getAhorroMinimo() != null) {
            ok = ok && user.getAhorro() != null && user.getAhorro() >= ach.getAhorroMinimo();
        }
        if (ach.getLeccionesMinimas() != null) {
            ok = ok && user.getLeccionesCompletadas() != null
                    && user.getLeccionesCompletadas() >= ach.getLeccionesMinimas();
        }
        if (ach.getRetosMinimos() != null) {
            ok = ok && user.getRetosAceptados() != null
                    && user.getRetosAceptados() >= ach.getRetosMinimos();
        }
        return ok;
    }

    public Achievement save(Achievement achievement) {
        return achievementRepository.save(achievement);
    }
}
