package ru.dayneko.authorization.model.token;

import lombok.*;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Контейнер-обертка для работы с провайдером.
 * Является композицией RawAccessToken'a и
 * данных пользователя.
 *
 * @author dayneko_si
 * @since 13/05.2019
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private JwtToken rawAccessJwtToken;
    private UserContext userContext;

    public JwtAuthenticationToken(JwtToken rawAccessJwtToken) {
        super(null);
        this.rawAccessJwtToken = rawAccessJwtToken;
        super.setAuthenticated(false);
    }

    public JwtAuthenticationToken(UserContext userContext, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.eraseCredentials();
        this.userContext = userContext;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return rawAccessJwtToken;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.rawAccessJwtToken = null;
    }
}
