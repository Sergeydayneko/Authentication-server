package ru.dayneko.authorization.model.token;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import ru.dayneko.authorization.exceptions.JwtExpiredTokenException;

import java.util.Optional;

/**
 * Интерфейс JWT токена
 *
 * @author dayneko_si
 * @since 07.05.2019
 */
public interface JwtToken {
    Logger logger = LoggerFactory.getLogger(JwtToken.class);
    String getToken();
    Jws<Claims> getClaims();

    /**
     * Дефолтный метод каждой из модели токена для
     * получения данных по субъекту
     *
     * @return строчное предсталвение субъекта
     */
    default String getSubject() {
        return this.getClaims().getBody().getSubject();
    }


    /**
     * Дефолтный метод для получения объекта Jws<Claims>
     *
     * @param signingKey - подпись токена
     * @return объект с Claims
     * @throws BadCredentialsException - неверные данные пользователя
     * @throws JwtExpiredTokenException - истечения срока действия токена
     */
    default Jws<Claims> parseClaims(String signingKey) throws BadCredentialsException, JwtExpiredTokenException {
        try {
            return Jwts.parser().setSigningKey(signingKey.getBytes()).parseClaimsJws(this.getToken());
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException ex) {
            logger.error("Invalid token");
            throw new BadCredentialsException("Invalid token", ex);
        } catch (ExpiredJwtException expiredEx) {
            logger.info("Token duration has been expired");
            throw new JwtExpiredTokenException(this, "Token duration has been expired", expiredEx);
        }
    }


    /**################################ Static JWT Methods ################################**/

    /**
     * Метод получения RefreshToken'a через
     * текущий RawAccessToken. Идет обновление
     * назависимоного объекта, поэтому данный метод
     * логически обоснован. Можно привязать к реализации
     * и парсить как в parseClaims() методе.
     *
     * @param token      - RawAccess токен
     * @param signingKey - Подпись токена
     * @return объект RefreshJwtToken
     * @throws BadCredentialsException  - неверные данные пользователя
     * @throws JwtExpiredTokenException - истек срок действия  RawAccess токена
     */
    static Optional<RefreshJwtToken> createRefreshTokenFromRawAccessToken(RawAccessJwtToken token, String signingKey) throws BadCredentialsException, JwtExpiredTokenException {
        Jws<Claims> claims = token.parseClaims(signingKey);

        // todo доделать скопы когда будут требования
//        List scopes = claims.getBody().get("authorities", List.class);
//        if (scopes == null || scopes.isEmpty() || scopes.stream().noneMatch(scope -> Scopes.REFRESH_TOKEN.getAuthority().equals(scope))) {
//            return Optional.empty();
//        }

        return Optional.of(new RefreshJwtToken(claims));
    }
}
