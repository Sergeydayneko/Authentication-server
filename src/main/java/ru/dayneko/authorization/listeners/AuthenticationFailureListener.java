package ru.dayneko.authorization.listeners;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;
import ru.dayneko.authorization.services.LoginAttemptService;
import ru.dayneko.authorization.utils.IpAddressExtractor;

@Component
@AllArgsConstructor
public final class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
    private final LoginAttemptService loginAttempt;
    private final IpAddressExtractor ipAddressExtractor;

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent authenticationFailureBadCredentialsEvent) {
        loginAttempt.loginFailed(ipAddressExtractor.getClientIP());
    }
}
