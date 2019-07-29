package ru.dayneko.authorization.model.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.Data;

/**
 * Объект refresh токена
 *
 * @author dayneko_si
 * @since 22.05.2019
 */
@Data
public class RefreshJwtToken implements JwtToken {
    private String token;
    private Jws<Claims> claims;

    public RefreshJwtToken(String token) {
        this.token = token;
    }

    RefreshJwtToken(Jws<Claims> claims) {
        this.claims = claims;
    }
}
