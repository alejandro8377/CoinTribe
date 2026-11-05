package com.cointribe.backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Relación usuario ↔ reto (un usuario solo puede unirse una vez a cada reto).
 */
@Entity
@Table(name = "user_challenges", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "challenge_id"})
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserChallenge extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    private Double ahorroAcumulado = 0.0;

    private LocalDateTime fechaUnion = LocalDateTime.now();

    private Boolean completado = false;
}
