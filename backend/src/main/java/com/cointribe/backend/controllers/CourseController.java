package com.cointribe.backend.controllers;

import com.cointribe.backend.dto.CompleteLessonRequest;
import com.cointribe.backend.dto.CourseCreateRequest;
import com.cointribe.backend.dto.CourseProgressRequest;
import com.cointribe.backend.dto.CourseResponseDto;
import com.cointribe.backend.dto.CourseUpdateRequest;
import com.cointribe.backend.dto.UserResponseDto;
import com.cointribe.backend.models.Course;
import com.cointribe.backend.models.User;
import com.cointribe.backend.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "*")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<CourseResponseDto>> getAll() {
        return ResponseEntity.ok(courseService.getAllCourses().stream()
                .map(CourseResponseDto::from)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDto> getById(@PathVariable Long id) {
        return courseService.getById(id)
                .map(CourseResponseDto::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/tema/{tema}")
    public ResponseEntity<List<CourseResponseDto>> getByTema(@PathVariable String tema) {
        return ResponseEntity.ok(courseService.getByTema(tema).stream()
                .map(CourseResponseDto::from)
                .toList());
    }

    @PutMapping("/{id}/progress")
    public ResponseEntity<CourseResponseDto> updateProgress(@PathVariable Long id,
                                                            @RequestBody CourseProgressRequest body) {
        Course course = courseService.updateProgress(id, body.progreso());
        return ResponseEntity.ok(CourseResponseDto.from(course));
    }

    @PostMapping("/complete-lesson")
    public ResponseEntity<UserResponseDto> completeLesson(@RequestBody CompleteLessonRequest body) {
        User user = courseService.completeLesson(body.userId(), body.lecciones());
        return ResponseEntity.ok(UserResponseDto.from(user));
    }

    /** POST /api/courses — Crear curso */
    @PostMapping
    public ResponseEntity<CourseResponseDto> create(@RequestBody CourseCreateRequest body) {
        Course course = courseService.createCourse(
                body.titulo(), body.tema(), body.descripcion(), body.totalLecciones());
        return ResponseEntity.status(HttpStatus.CREATED).body(CourseResponseDto.from(course));
    }

    /** PUT /api/courses/{id} — Actualizar curso */
    @PutMapping("/{id}")
    public ResponseEntity<CourseResponseDto> update(@PathVariable Long id,
                                                    @RequestBody CourseUpdateRequest body) {
        Course course = courseService.updateCourse(
                id,
                body.titulo(),
                body.tema(),
                body.descripcion(),
                body.totalLecciones(),
                body.progreso()
        );
        return ResponseEntity.ok(CourseResponseDto.from(course));
    }

    /** DELETE /api/courses/{id} — Eliminar curso */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}
