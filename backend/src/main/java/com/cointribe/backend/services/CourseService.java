package com.cointribe.backend.services;

import com.cointribe.backend.exceptions.BusinessException;
import com.cointribe.backend.models.Course;
import com.cointribe.backend.models.User;
import com.cointribe.backend.repositories.CourseRepository;
import com.cointribe.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final AchievementService achievementService;

    @Autowired
    public CourseService(CourseRepository courseRepository,
                         UserRepository userRepository,
                         AchievementService achievementService) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.achievementService = achievementService;
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Optional<Course> getById(Long id) {
        return courseRepository.findById(id);
    }

    public List<Course> getByTema(String tema) {
        return courseRepository.findByTemaIgnoreCase(tema);
    }

    /**
     * REGLA DE NEGOCIO 5: El progreso de un curso siempre debe estar
     * entre 0 y 100 inclusive. Cualquier valor fuera de rango se rechaza
     * (no se permite progreso negativo ni superior al 100 %).
     */
    @Transactional
    public Course updateProgress(Long courseId, Integer nuevoProgreso) {
        if (nuevoProgreso == null) {
            throw new BusinessException("El progreso es obligatorio.");
        }
        if (nuevoProgreso < 0 || nuevoProgreso > 100) {
            throw new BusinessException(
                    "El progreso del curso debe estar entre 0 y 100. Valor recibido: " + nuevoProgreso);
        }

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new BusinessException("Curso no encontrado."));

        // Solo se permite avanzar o mantener; no retroceder (regla adicional de negocio)
        if (course.getProgreso() != null && nuevoProgreso < course.getProgreso()) {
            throw new BusinessException(
                    "No se permite reducir el progreso de un curso. Progreso actual: "
                            + course.getProgreso() + "%");
        }

        course.setProgreso(nuevoProgreso);
        return courseRepository.save(course);
    }

    /**
     * Marca lecciones completadas para un usuario y actualiza su contador.
     * También evalúa logros.
     */
    @Transactional
    public User completeLesson(Long userId, int leccionesAdicionales) {
        if (leccionesAdicionales <= 0) {
            throw new BusinessException("Debes completar al menos una lección.");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("Usuario no encontrado."));
        user.setLeccionesCompletadas(user.getLeccionesCompletadas() + leccionesAdicionales);
        User saved = userRepository.save(user);
        achievementService.evaluateAndUnlock(saved);
        return saved;
    }

    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }
<<<<<<< HEAD
=======

    /**
     * Crear un nuevo curso.
     */
    @Transactional
    public Course createCourse(String titulo, String tema, String descripcion, Integer totalLecciones) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new BusinessException("El título del curso es obligatorio.");
        }
        if (totalLecciones != null && totalLecciones < 0) {
            throw new BusinessException("El total de lecciones no puede ser negativo.");
        }

        Course course = new Course();
        course.setTitulo(titulo.trim());
        course.setTema(tema != null ? tema.trim() : null);
        course.setDescripcion(descripcion != null ? descripcion.trim() : null);
        course.setProgreso(0);
        course.setTotalLecciones(totalLecciones != null ? totalLecciones : 0);
        return courseRepository.save(course);
    }

    /**
     * Actualizar un curso existente.
     */
    @Transactional
    public Course updateCourse(Long id, String titulo, String tema, String descripcion,
                               Integer totalLecciones, Integer progreso) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Curso no encontrado."));

        if (titulo != null && !titulo.trim().isEmpty()) {
            course.setTitulo(titulo.trim());
        }
        if (tema != null) {
            course.setTema(tema.trim());
        }
        if (descripcion != null) {
            course.setDescripcion(descripcion.trim());
        }
        if (totalLecciones != null) {
            if (totalLecciones < 0) {
                throw new BusinessException("El total de lecciones no puede ser negativo.");
            }
            course.setTotalLecciones(totalLecciones);
        }
        if (progreso != null) {
            if (progreso < 0 || progreso > 100) {
                throw new BusinessException("El progreso del curso debe estar entre 0 y 100.");
            }
            course.setProgreso(progreso);
        }
        return courseRepository.save(course);
    }

    /**
     * Eliminar un curso.
     */
    @Transactional
    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new BusinessException("Curso no encontrado.");
        }
        courseRepository.deleteById(id);
    }
>>>>>>> e5646a2 (Nuevos endpoints e implementacion de base de datos en NEON)
}
