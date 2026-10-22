package com.cointribe.backend.services;

<<<<<<< HEAD
=======
import com.cointribe.backend.exceptions.BusinessException;
>>>>>>> e5646a2 (Nuevos endpoints e implementacion de base de datos en NEON)
import com.cointribe.backend.models.Coin;
import com.cointribe.backend.repositories.CoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
<<<<<<< HEAD
=======
import org.springframework.transaction.annotation.Transactional;
>>>>>>> e5646a2 (Nuevos endpoints e implementacion de base de datos en NEON)

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
<<<<<<< HEAD
=======

    @Transactional
    public Coin createCoin(String name, String symbol, Double price) {
        if (name == null || name.trim().isEmpty()) {
            throw new BusinessException("El nombre de la moneda es obligatorio.");
        }
        if (symbol == null || symbol.trim().isEmpty()) {
            throw new BusinessException("El símbolo de la moneda es obligatorio.");
        }
        Coin coin = new Coin();
        coin.setName(name.trim());
        coin.setSymbol(symbol.trim().toUpperCase());
        coin.setPrice(price != null ? price : 0.0);
        return coinRepository.save(coin);
    }

    @Transactional
    public Coin updateCoin(Long id, String name, String symbol, Double price) {
        Coin coin = coinRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Moneda no encontrada."));
        if (name != null && !name.trim().isEmpty()) {
            coin.setName(name.trim());
        }
        if (symbol != null && !symbol.trim().isEmpty()) {
            coin.setSymbol(symbol.trim().toUpperCase());
        }
        if (price != null) {
            if (price < 0) {
                throw new BusinessException("El precio no puede ser negativo.");
            }
            coin.setPrice(price);
        }
        return coinRepository.save(coin);
    }

    @Transactional
    public void deleteCoin(Long id) {
        if (!coinRepository.existsById(id)) {
            throw new BusinessException("Moneda no encontrada.");
        }
        coinRepository.deleteById(id);
    }
>>>>>>> e5646a2 (Nuevos endpoints e implementacion de base de datos en NEON)
}
