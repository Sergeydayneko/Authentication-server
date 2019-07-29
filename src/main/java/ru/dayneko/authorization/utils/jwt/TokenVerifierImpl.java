package ru.dayneko.authorization.utils.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.stereotype.Service;
import ru.dayneko.authorization.services.UserService;
import ru.dayneko.authorization.utils.jwt.filter.CheckMiddleware;
import ru.dayneko.authorization.utils.jwt.filter.DateCheck;
import ru.dayneko.authorization.utils.jwt.filter.UserCheck;

/**
 * Фильтр основанный на chain of responsibility
 *
 * @author dayneko_si
 * @since 24.05.2019
 */
@Service
public class TokenVerifierImpl implements TokenVerifier {
    private final CheckMiddleware chainOfVerifiers;

    public TokenVerifierImpl(UserService userService) {
        this.chainOfVerifiers = new DateCheck().linkWith(new UserCheck(userService));
    }

    @Override
    public boolean verifyClaims(Jws<Claims> claimsJws) {
        return chainOfVerifiers.check(claimsJws);
    }
}
