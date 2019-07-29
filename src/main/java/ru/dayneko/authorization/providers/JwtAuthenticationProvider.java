package ru.dayneko.authorization.providers;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import ru.dayneko.authorization.config.JwtConfig;
import ru.dayneko.authorization.model.token.JwtAuthenticationToken;
import ru.dayneko.authorization.model.token.UserContext;

import java.util.Collections;
import java.util.List;

/**
 * Провайдер предоставляющий проверку JWT токена
 *
 * @author dayneko_si
 * @since 24.04.2019
 */
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final Logger logger = LoggerFactory.getLogger(JwtAuthenticationProvider.class);
    private final JwtConfig jwtConfig;

    public JwtAuthenticationProvider(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        logger.debug("Starting to check access token information");

        val rawAccessJwtToken = ((JwtAuthenticationToken)authentication).getRawAccessJwtToken();

        // todo try catch можно будет убрать после переоформления исключения просроченного токена
        // todo полу ролей будет представлено как "roles"
        // todo Убрать лишний Provider, обеспечить сохранение контекста через один провайдер
        // Провайдер на юзера проверяет при первом вхождении, текущий при наличии токена
        try {
            Jws<Claims> claims = rawAccessJwtToken.parseClaims(jwtConfig.getSecret());


            List<GrantedAuthority> authorities = Collections.emptyList();
            // todo внести правки, когда появитяся роли на URL
//                    ((List<String>) claims.getBody().get("authorities", List.class))
//                            .stream().map(SimpleGrantedAuthority::new)
//                            .collect(Collectors.toList());

            UserContext userContext = UserContext.create(claims.getBody().getSubject(), null, authorities);

            return new JwtAuthenticationToken(userContext, authorities);

        } catch (Exception e) {
            //todo сделать обработку
            e.printStackTrace();
        }


        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));

    }
}
