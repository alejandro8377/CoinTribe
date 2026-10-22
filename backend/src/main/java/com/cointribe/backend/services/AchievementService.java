package com.cointribe.backend.services;

<<<<<<< HEAD
=======
import com.cointribe.backend.exceptions.BusinessException;
>>>>>>> e5646a2 (Nuevos endpoints e implementacion de base de datos en NEON)
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
<<<<<<< HEAD
=======
import java.util.Optional;
>>>>>>> e5646a2 (Nuevos endpoints e implementacion de base de datos en NEON)

/**
 * Servicio de logros. Evalúa automáticamente si el usuario cumple
 * las condiciones de cada logro y lo desbloquea.
 * El desbloqueo queda registrado para que la progresión sea visible y auditable.
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
<<<<<<< HEAD
=======

    public Optional<Achievement> getById(Long id) {
        return achievementRepository.findById(id);
    }

    /**
     * Crear un nuevo logro.
     */
    @Transactional
    public Achievement createAchievement(String codigo, String icono, String nombre, String pista,
                                         Double ahorroMinimo, Integer leccionesMinimas, Integer retosMinimos) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new BusinessException("El código del logro es obligatorio.");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new BusinessException("El nombre del logro es obligatorio.");
        }
        if (achievementRepository.findByCodigo(codigo.trim()).isPresent()) {
            throw new BusinessException("Ya existe un logro con ese código.");
        }

        Achievement ach = new Achievement();
        ach.setCodigo(codigo.trim());
        ach.setIcono(icono);
        ach.setNombre(nombre.trim());
        ach.setPista(pista);
        ach.setAhorroMinimo(ahorroMinimo);
        ach.setLeccionesMinimas(leccionesMinimas);
        ach.setRetosMinimos(retosMinimos);
        return achievementRepository.save(ach);
    }

    /**
     * Actualizar un logro existente.
     */
    @Transactional
    public Achievement updateAchievement(Long id, String codigo, String icono, String nombre, String pista,
                                         Double ahorroMinimo, Integer leccionesMinimas, Integer retosMinimos) {
        Achievement ach = achievementRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Logro no encontrado."));

        if (codigo != null && !codigo.trim().isEmpty()) {
            achievementRepository.findByCodigo(codigo.trim()).ifPresent(existing -> {
                if (!existing.getId().equals(id)) {
                    throw new BusinessException("Ya existe un logro con ese código.");
                }
            });
            ach.setCodigo(codigo.trim());
        }
        if (icono != null) {
            ach.setIcono(icono);
        }
        if (nombre != null && !nombre.trim().isEmpty()) {
            ach.setNombre(nombre.trim());
        }
        if (pista != null) {
            ach.setPista(pista);
        }
        if (ahorroMinimo != null) {
            ach.setAhorroMinimo(ahorroMinimo);
        }
        if (leccionesMinimas != null) {
            ach.setLeccionesMinimas(leccionesMinimas);
        }
        if (retosMinimos != null) {
            ach.setRetosMinimos(retosMinimos);
        }
        return achievementRepository.save(ach);
    }

    /**
     * Eliminar un logro.
     */
    @Transactional
    public void deleteAchievement(Long id) {
        if (!achievementRepository.existsById(id)) {
            throw new BusinessException("Logro no encontrado.");
        }
        achievementRepository.deleteById(id);
    }
>>>>>>> e5646a2 (Nuevos endpoints e implementacion de base de datos en NEON)
}
