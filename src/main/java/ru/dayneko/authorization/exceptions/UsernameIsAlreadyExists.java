package ru.dayneko.authorization.exceptions;

import org.springframework.security.core.AuthenticationException;

/**
 * Объект Exception при попытке создания пользователя
 * с существующим именем.
 *
 * @author dayneko_si
 * @since 11.06.2019
 */
public class UsernameIsAlreadyExists extends AuthenticationException {
    public UsernameIsAlreadyExists(String msg) {
        super(msg);
    }
}
