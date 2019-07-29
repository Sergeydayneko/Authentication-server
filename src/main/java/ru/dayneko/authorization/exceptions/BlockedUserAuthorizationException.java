package ru.dayneko.authorization.exceptions;

import org.springframework.security.core.AuthenticationException;

/**
 * Объект Exception для отслеживания исключения
 * при попытке зайти пользователю, который заблокирован по IP
 * адресу
 *
 * @author dayneko_si
 * @since 27.05.2019
 */
public final class BlockedUserAuthorizationException extends AuthenticationException {
    public BlockedUserAuthorizationException(String message) {
        super(message);
    }
}
