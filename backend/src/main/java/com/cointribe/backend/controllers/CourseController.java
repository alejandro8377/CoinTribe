package com.cointribe.backend.controllers;

import com.cointribe.backend.models.Course;
import com.cointribe.backend.models.User;
import com.cointribe.backend.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<List<Course>> getAll() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getById(@PathVariable Long id) {
        return courseService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/tema/{tema}")
    public ResponseEntity<List<Course>> getByTema(@PathVariable String tema) {
        return ResponseEntity.ok(courseService.getByTema(tema));
    }

    @PutMapping("/{id}/progress")
    public ResponseEntity<Course> updateProgress(@PathVariable Long id,
                                                 @RequestBody Map<String, Integer> body) {
        Course course = courseService.updateProgress(id, body.get("progreso"));
        return ResponseEntity.ok(course);
    }

    @PostMapping("/complete-lesson")
    public ResponseEntity<User> completeLesson(@RequestBody Map<String, Object> body) {
        Long userId = Long.valueOf(body.get("userId").toString());
        int lecciones = Integer.parseInt(body.get("lecciones").toString());
        User user = courseService.completeLesson(userId, lecciones);
        user.setPassword(null);
        return ResponseEntity.ok(user);
    }
}
