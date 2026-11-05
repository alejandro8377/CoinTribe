package com.cointribe.backend.controllers;

import com.cointribe.backend.dto.CoinRequest;
import com.cointribe.backend.dto.CoinResponseDto;
import com.cointribe.backend.dto.ValidationGroups;
import com.cointribe.backend.models.Coin;
import com.cointribe.backend.services.CoinService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coins")
@CrossOrigin(origins = "*")
@Tag(name = "Monedas", description = "Operaciones para consultar y administrar monedas")
public class CoinController {

    private final CoinService coinService;

    @Autowired
    public CoinController(CoinService coinService) {
        this.coinService = coinService;
    }

    @GetMapping
    @Operation(summary = "Listar monedas")
    @ApiResponse(responseCode = "200", description = "Lista de monedas")
    public ResponseEntity<List<CoinResponseDto>> getAllCoins() {
        return ResponseEntity.ok(coinService.getAllCoins().stream()
                .map(CoinResponseDto::from)
                .toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener moneda")
    @ApiResponse(responseCode = "200", description = "Moneda encontrada")
    public ResponseEntity<CoinResponseDto> getCoinById(@PathVariable Long id) {
        return coinService.getCoinById(id)
                .map(CoinResponseDto::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear moneda")
    @ApiResponse(responseCode = "201", description = "Moneda creada")
    public ResponseEntity<CoinResponseDto> createCoin(
            @RequestBody @Validated(ValidationGroups.Create.class) CoinRequest body) {
        Coin newCoin = coinService.createCoin(body.name(), body.symbol(), body.price());
        return ResponseEntity.status(HttpStatus.CREATED).body(CoinResponseDto.from(newCoin));
    }

    /** PUT /api/coins/{id} — Actualizar moneda */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar moneda")
    @ApiResponse(responseCode = "200", description = "Moneda actualizada")
    public ResponseEntity<CoinResponseDto> updateCoin(@PathVariable Long id,
            @RequestBody @Validated(ValidationGroups.Update.class) CoinRequest body) {
        Coin coin = coinService.updateCoin(id, body.name(), body.symbol(), body.price());
        return ResponseEntity.ok(CoinResponseDto.from(coin));
    }

    /** DELETE /api/coins/{id} — Eliminar moneda */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar moneda")
    @ApiResponse(responseCode = "204", description = "Moneda eliminada")
    public ResponseEntity<Void> deleteCoin(@PathVariable Long id) {
        coinService.deleteCoin(id);
        return ResponseEntity.noContent().build();
    }
}
