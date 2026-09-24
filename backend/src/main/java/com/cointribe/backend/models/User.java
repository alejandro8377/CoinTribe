package com.cointribe.backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private Integer nivel = 1;

    private Double ahorro = 0.0;

    private Double meta = 500000.0;

    private Integer leccionesCompletadas = 0;

    private Integer retosAceptados = 0;

    private LocalDateTime fechaRegistro = LocalDateTime.now();

    private Boolean activo = true;
}
