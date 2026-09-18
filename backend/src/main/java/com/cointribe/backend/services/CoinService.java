package com.cointribe.backend.services;

import com.cointribe.backend.models.Coin;
import com.cointribe.backend.repositories.CoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CoinService {

    private final CoinRepository coinRepository;

    @Autowired
    public CoinService(CoinRepository coinRepository) {
        this.coinRepository = coinRepository;
    }

    public List<Coin> getAllCoins() {
        return coinRepository.findAll();
    }

    public Optional<Coin> getCoinById(Long id) {
        return coinRepository.findById(id);
    }

    public Coin saveCoin(Coin coin) {
        return coinRepository.save(coin);
    }
}
