package ru.dayneko.authorization.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class InvalidJwtTokenException extends RuntimeException {
    public InvalidJwtTokenException(String message) {
        super(message);
    }
}
