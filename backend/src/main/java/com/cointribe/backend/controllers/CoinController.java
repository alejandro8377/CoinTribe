package com.cointribe.backend.controllers;

import com.cointribe.backend.models.Coin;
import com.cointribe.backend.services.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
<<<<<<< HEAD

@RestController
@RequestMapping("/api/coins")
@CrossOrigin(origins = "*") // Para evitar problemas de CORS con el frontend
=======
import java.util.Map;

@RestController
@RequestMapping("/api/coins")
@CrossOrigin(origins = "*")
>>>>>>> e5646a2 (Nuevos endpoints e implementacion de base de datos en NEON)
public class CoinController {

    private final CoinService coinService;

    @Autowired
    public CoinController(CoinService coinService) {
        this.coinService = coinService;
    }

    @GetMapping
    public ResponseEntity<List<Coin>> getAllCoins() {
<<<<<<< HEAD
        return new ResponseEntity<>(coinService.getAllCoins(), HttpStatus.OK);
=======
        return ResponseEntity.ok(coinService.getAllCoins());
>>>>>>> e5646a2 (Nuevos endpoints e implementacion de base de datos en NEON)
    }

    @GetMapping("/{id}")
    public ResponseEntity<Coin> getCoinById(@PathVariable Long id) {
        return coinService.getCoinById(id)
<<<<<<< HEAD
                .map(coin -> new ResponseEntity<>(coin, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Coin> createCoin(@RequestBody Coin coin) {
        Coin newCoin = coinService.saveCoin(coin);
        return new ResponseEntity<>(newCoin, HttpStatus.CREATED);
=======
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Coin> createCoin(@RequestBody Map<String, Object> body) {
        String name = body.get("name") != null ? body.get("name").toString() : null;
        String symbol = body.get("symbol") != null ? body.get("symbol").toString() : null;
        Double price = body.get("price") != null ? Double.valueOf(body.get("price").toString()) : null;
        Coin newCoin = coinService.createCoin(name, symbol, price);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCoin);
    }

    /** PUT /api/coins/{id} — Actualizar moneda */
    @PutMapping("/{id}")
    public ResponseEntity<Coin> updateCoin(@PathVariable Long id,
                                           @RequestBody Map<String, Object> body) {
        String name = body.get("name") != null ? body.get("name").toString() : null;
        String symbol = body.get("symbol") != null ? body.get("symbol").toString() : null;
        Double price = body.get("price") != null ? Double.valueOf(body.get("price").toString()) : null;
        Coin coin = coinService.updateCoin(id, name, symbol, price);
        return ResponseEntity.ok(coin);
    }

    /** DELETE /api/coins/{id} — Eliminar moneda */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoin(@PathVariable Long id) {
        coinService.deleteCoin(id);
        return ResponseEntity.noContent().build();
>>>>>>> e5646a2 (Nuevos endpoints e implementacion de base de datos en NEON)
    }
}
