package com.cointribe.backend.dto;

import com.cointribe.backend.models.Coin;

public record CoinResponseDto(
        Long id,
        String name,
        String symbol,
        Double price
) {

    public static CoinResponseDto from(Coin coin) {
        return new CoinResponseDto(
                coin.getId(),
                coin.getName(),
                coin.getSymbol(),
                coin.getPrice()
        );
    }
}
