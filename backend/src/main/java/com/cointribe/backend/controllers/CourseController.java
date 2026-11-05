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
@RequestMapping("/api/courses")
@CrossOrigin(origins = "*")
@Tag(name = "Cursos", description = "Operaciones para consultar y administrar cursos")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    @Operation(summary = "Listar cursos")
    @ApiResponse(responseCode = "200", description = "Lista de cursos")
    public ResponseEntity<List<CourseResponseDto>> getAll() {
        return ResponseEntity.ok(courseService.getAllCourses().stream()
                .map(CourseResponseDto::from)
                .toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener curso")
    @ApiResponse(responseCode = "200", description = "Curso encontrado")
    public ResponseEntity<CourseResponseDto> getById(@PathVariable Long id) {
        return courseService.getById(id)
                .map(CourseResponseDto::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/tema/{tema}")
    @Operation(summary = "Filtrar cursos por tema")
    @ApiResponse(responseCode = "200", description = "Cursos del tema solicitado")
    public ResponseEntity<List<CourseResponseDto>> getByTema(@PathVariable String tema) {
        return ResponseEntity.ok(courseService.getByTema(tema).stream()
                .map(CourseResponseDto::from)
                .toList());
    }

    @PutMapping("/{id}/progress")
    @Operation(summary = "Actualizar progreso de curso")
    @ApiResponse(responseCode = "200", description = "Progreso actualizado")
    public ResponseEntity<CourseResponseDto> updateProgress(@PathVariable Long id,
            @RequestBody @Valid CourseProgressRequest body) {
        Course course = courseService.updateProgress(id, body.progreso());
        return ResponseEntity.ok(CourseResponseDto.from(course));
    }

    @PostMapping("/complete-lesson")
    @Operation(summary = "Completar lecciones")
    @ApiResponse(responseCode = "200", description = "Progreso del usuario actualizado")
    public ResponseEntity<UserResponseDto> completeLesson(@RequestBody @Valid CompleteLessonRequest body) {
        User user = courseService.completeLesson(body.userId(), body.lecciones());
        return ResponseEntity.ok(UserResponseDto.from(user));
    }

    /** POST /api/courses — Crear curso */
    @PostMapping
    @Operation(summary = "Crear curso")
    @ApiResponse(responseCode = "201", description = "Curso creado")
    public ResponseEntity<CourseResponseDto> create(@RequestBody @Valid CourseCreateRequest body) {
        Course course = courseService.createCourse(
                body.titulo(), body.tema(), body.descripcion(), body.totalLecciones());
        return ResponseEntity.status(HttpStatus.CREATED).body(CourseResponseDto.from(course));
    }

    /** PUT /api/courses/{id} — Actualizar curso */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar curso")
    @ApiResponse(responseCode = "200", description = "Curso actualizado")
    public ResponseEntity<CourseResponseDto> update(@PathVariable Long id,
            @RequestBody @Valid CourseUpdateRequest body) {
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
    @Operation(summary = "Eliminar curso")
    @ApiResponse(responseCode = "204", description = "Curso eliminado")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}
