package com.cointribe.backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "courses")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Course extends BaseEntity {

    @Column(nullable = false)
    private String titulo;

    private String tema;

    @Column(length = 1000)
    private String descripcion;

    /** Progreso global del curso (0-100). */
    private Integer progreso = 0;

    private Integer totalLecciones = 0;
}
