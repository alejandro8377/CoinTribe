package com.cointribe.backend.repositories;

import com.cointribe.backend.models.Coin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CoinRepository extends JpaRepository<Coin, Long> {
    Optional<Coin> findBySymbolIgnoreCase(String symbol);
    List<Coin> findByNameContainingIgnoreCase(String name);
}
