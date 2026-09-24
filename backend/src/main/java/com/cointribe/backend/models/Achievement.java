package com.cointribe.backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "achievements")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Achievement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String codigo;

    private String icono;

    @Column(nullable = false)
    private String nombre;

    private String pista;

    /** Condición mínima de ahorro para desbloquear (opcional). */
    private Double ahorroMinimo;

    /** Condición mínima de lecciones completadas. */
    private Integer leccionesMinimas;

    /** Condición mínima de retos aceptados. */
    private Integer retosMinimos;
}
