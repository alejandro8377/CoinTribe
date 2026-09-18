package com.cointribe.backend.controllers;

import com.cointribe.backend.models.Coin;
import com.cointribe.backend.services.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coins")
@CrossOrigin(origins = "*") // Para evitar problemas de CORS con el frontend
public class CoinController {

    private final CoinService coinService;

    @Autowired
    public CoinController(CoinService coinService) {
        this.coinService = coinService;
    }

    @GetMapping
    public ResponseEntity<List<Coin>> getAllCoins() {
        return new ResponseEntity<>(coinService.getAllCoins(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Coin> getCoinById(@PathVariable Long id) {
        return coinService.getCoinById(id)
                .map(coin -> new ResponseEntity<>(coin, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Coin> createCoin(@RequestBody Coin coin) {
        Coin newCoin = coinService.saveCoin(coin);
        return new ResponseEntity<>(newCoin, HttpStatus.CREATED);
    }
}
