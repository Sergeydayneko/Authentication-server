package ru.dayneko.authorization.model.token;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class UserContext {
    private final String login;
    private final String fio; //todo для чего используется fio и нужно ли оно
    private final Collection<? extends GrantedAuthority> authorities;

    private UserContext(String login, String fio, Collection<? extends GrantedAuthority> authorities) {
        this.fio = fio;
        this.login = login;
        this.authorities = authorities;
    }

    public static UserContext create(String username, String fio, Collection<? extends GrantedAuthority> authorities) {
        if (StringUtils.isBlank(username)) throw new IllegalArgumentException("Username is blank: " + username);
        return new UserContext(username, fio, authorities);
    }
}
