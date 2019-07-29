package ru.dayneko.authorization.filters;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

abstract public class AuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {
    public AuthenticationProcessingFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    public AuthenticationProcessingFilter(RequestMatcher matcher) {
        super(matcher);
    }

    public AbstractAuthenticationProcessingFilter setAuthManager(AuthenticationManager authenticationManager) {
        this.setAuthenticationManager(authenticationManager);
        return this;
    }
}
