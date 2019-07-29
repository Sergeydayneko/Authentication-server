package ru.dayneko.authorization.exceptions;

import org.springframework.security.core.AuthenticationException;

/**
 * Объект Exception при неверных данных
 * аутентификации пользователя
 *
 * @author dayneko_si
 * @since 07.05.2019
 */
final public class UserCredentialsAreNotValid extends AuthenticationException {
    public UserCredentialsAreNotValid(String msg) {
        super(msg);
    }
}
