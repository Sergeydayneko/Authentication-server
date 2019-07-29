package ru.dayneko.authorization.providers;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.dayneko.authorization.services.UserService;

import java.util.Collections;

/**
 * Провайдер предоставляющий проверку пользователя (по логину и паролю)
 *
 * @author dayneko_si
 * @since 24.04.2019
 */
@Component
@AllArgsConstructor
public class UserAuthenticationProvider implements AuthenticationProvider {
    private BCryptPasswordEncoder encoder;
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("User provider");
        val username = (String) authentication.getPrincipal();
        val password = (String) authentication.getCredentials();

        val user = userService.getUserByLogin(username).orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
        // todo ввести хэширование

        if (!userService.checkUserCredentials(user, password)) {
            throw new BadCredentialsException("Authentication Failed. Username or Password not valid for " + username);
        }

        // todo ввести роли, когда они появятся
        return new UsernamePasswordAuthenticationToken(
                          username
                        , password
                        , Collections.emptyList()
                );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
