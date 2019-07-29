package ru.dayneko.authorization.model.token;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import java.util.List;

// todo продумать объединение с объектом UserContext
@Data
@AllArgsConstructor
public class UserCredentials {
    private final String username;
    private final String password;
    private final List<GrantedAuthority> authorities;
}
