package com.cointribe.backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "achievements")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Achievement extends BaseEntity {

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
