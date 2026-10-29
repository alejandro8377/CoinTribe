package com.cointribe.backend.controllers;

import com.cointribe.backend.dto.CoinRequest;
import com.cointribe.backend.dto.CoinResponseDto;
import com.cointribe.backend.models.Coin;
import com.cointribe.backend.services.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coins")
@CrossOrigin(origins = "*")
public class CoinController {

    private final CoinService coinService;

    @Autowired
    public CoinController(CoinService coinService) {
        this.coinService = coinService;
    }

    @GetMapping
    public ResponseEntity<List<CoinResponseDto>> getAllCoins() {
        return ResponseEntity.ok(coinService.getAllCoins().stream()
                .map(CoinResponseDto::from)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CoinResponseDto> getCoinById(@PathVariable Long id) {
        return coinService.getCoinById(id)
                .map(CoinResponseDto::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CoinResponseDto> createCoin(@RequestBody CoinRequest body) {
        Coin newCoin = coinService.createCoin(body.name(), body.symbol(), body.price());
        return ResponseEntity.status(HttpStatus.CREATED).body(CoinResponseDto.from(newCoin));
    }

    /** PUT /api/coins/{id} — Actualizar moneda */
    @PutMapping("/{id}")
    public ResponseEntity<CoinResponseDto> updateCoin(@PathVariable Long id,
                                                       @RequestBody CoinRequest body) {
        Coin coin = coinService.updateCoin(id, body.name(), body.symbol(), body.price());
        return ResponseEntity.ok(CoinResponseDto.from(coin));
    }

    /** DELETE /api/coins/{id} — Eliminar moneda */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoin(@PathVariable Long id) {
        coinService.deleteCoin(id);
        return ResponseEntity.noContent().build();
    }
}
