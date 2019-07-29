package ru.dayneko.authorization.exceptions;

import org.springframework.security.core.AuthenticationException;
import ru.dayneko.authorization.model.token.JwtToken;

/**
 * Объект Exception при окончании срока действия токена
 *
 * @author dayneko_si
 * @since 07.05.2019
 */
final public class JwtExpiredTokenException extends AuthenticationException {
    private JwtToken token;

    public JwtExpiredTokenException(JwtToken token, String msg, Throwable t) {
        super(msg, t);
        this.token = token;
    }

    public String token() {
        return this.token.getToken();
    }
}
