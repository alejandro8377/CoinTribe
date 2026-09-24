package com.cointribe.backend.exceptions;

/**
 * Excepción de reglas de negocio de CoinTribe.
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}
