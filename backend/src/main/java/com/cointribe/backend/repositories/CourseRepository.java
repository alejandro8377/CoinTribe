package com.cointribe.backend.repositories;

import com.cointribe.backend.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByTemaIgnoreCase(String tema);
}
