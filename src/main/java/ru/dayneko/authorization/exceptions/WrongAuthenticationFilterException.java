package ru.dayneko.authorization.exceptions;

/**
 * Исключение при неверном указании типа фильтра
 * аутентификации в фабричном методе
 *
 * @author dayneko_si
 * @since 07.06.2019
 */
public class WrongAuthenticationFilterException extends RuntimeException {
    public WrongAuthenticationFilterException (String message) {
        super(message);
    }

}
