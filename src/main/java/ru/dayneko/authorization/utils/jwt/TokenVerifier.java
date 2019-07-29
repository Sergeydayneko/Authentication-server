package ru.dayneko.authorization.utils.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

/**
 * Интерфейс проверки действия токена
 *
 * @author dayneko_si
 * @since 24.05.2019
 */
public interface TokenVerifier {
    boolean verifyClaims(Jws<Claims> claimsJws);
}
