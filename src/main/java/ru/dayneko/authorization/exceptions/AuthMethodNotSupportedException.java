package ru.dayneko.authorization.exceptions;

import org.springframework.security.authentication.AuthenticationServiceException;

/**
 * Объект Exception для отслеживания исключения
 * при неверном Http запросе на попытку аутентификации
 *
 * @author  dayneko_si
 * @since 07.05.2019
 */
final public class AuthMethodNotSupportedException extends AuthenticationServiceException {
    public AuthMethodNotSupportedException(String msg) {
        super(msg);
    }
}
