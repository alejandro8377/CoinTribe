package com.cointribe.backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Post extends BaseEntity {

    @Column(nullable = false)
    private String autor;

    private String etiqueta;

    @Column(nullable = false)
    private String titulo;

    @Column(length = 2000, nullable = false)
    private String cuerpo;

    private Integer likes = 0;

    private LocalDateTime fecha = LocalDateTime.now();
}
