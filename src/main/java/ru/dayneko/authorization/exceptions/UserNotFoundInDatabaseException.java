package ru.dayneko.authorization.exceptions;

/**
 * Исключение при ненахождении пользователя в базе данных
 *
 * @author dayneko_si
 * @since 13.05.2019
 */
final public class UserNotFoundInDatabaseException extends RuntimeException {
    public UserNotFoundInDatabaseException (String message) {
        super(message);
    }
}
