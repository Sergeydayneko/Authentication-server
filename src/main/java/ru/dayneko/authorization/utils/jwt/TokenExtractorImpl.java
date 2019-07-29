package ru.dayneko.authorization.utils.jwt;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;
import ru.dayneko.authorization.config.JwtConfig;

@Component
@AllArgsConstructor
final public class TokenExtractorImpl implements TokenExtractor {
    private final JwtConfig jwtConfig;

    @Override
    public String extractTokenFromHeader(String payload) {
        if (StringUtils.isBlank(payload)) {
            throw new AuthenticationServiceException("Заголовок с токеном не может быть пустым!");
        }

        if (payload.length() < jwtConfig.getPrefix().length()) {
            throw new AuthenticationServiceException("Ошбочное число символок в заголовке токена.");
        }

        return payload.substring(jwtConfig.getPrefix().length()).trim();
    }
}
