package com.cointribe.backend.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "coins")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coin {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    private String symbol;
    
    private Double price;
}
