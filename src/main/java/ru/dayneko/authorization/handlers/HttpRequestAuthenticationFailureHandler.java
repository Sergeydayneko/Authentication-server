package ru.dayneko.authorization.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import ru.dayneko.authorization.exceptions.AuthMethodNotSupportedException;
import ru.dayneko.authorization.exceptions.BlockedUserAuthorizationException;
import ru.dayneko.authorization.exceptions.JwtExpiredTokenException;
import ru.dayneko.authorization.common.ErrorCode;
import ru.dayneko.authorization.common.ErrorResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
final public class HttpRequestAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private final ObjectMapper mapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request
                                        , HttpServletResponse response
                                        , AuthenticationException e) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        if (e instanceof BadCredentialsException || e instanceof UsernameNotFoundException) {
            mapper.writeValue(response.getWriter(), ErrorResponse.of("Wrong login or password", ErrorCode.AUTHENTICATION, HttpStatus.UNAUTHORIZED));
        } else if (e instanceof JwtExpiredTokenException) {
            mapper.writeValue(response.getWriter(), ErrorResponse.of("Token Expired", ErrorCode.JWT_TOKEN_EXPIRED, HttpStatus.UNAUTHORIZED));
        } else if (e instanceof AuthMethodNotSupportedException) {
            mapper.writeValue(response.getWriter(), ErrorResponse.of(e.getMessage(), ErrorCode.AUTHENTICATION, HttpStatus.UNAUTHORIZED));
        } else if( e instanceof BlockedUserAuthorizationException){
            mapper.writeValue(response.getWriter(), ErrorResponse.of(e.getMessage(), ErrorCode.AUTHENTICATION, HttpStatus.UNAUTHORIZED));
        }

        mapper.writeValue(response.getWriter(), ErrorResponse.of("Authentication error", ErrorCode.AUTHENTICATION, HttpStatus.UNAUTHORIZED));
    }
}
