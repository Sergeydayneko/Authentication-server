package ru.dayneko.authorization.filters.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import ru.dayneko.authorization.exceptions.AbsentLoginParametersException;
import ru.dayneko.authorization.exceptions.AuthMethodNotSupportedException;
import ru.dayneko.authorization.filters.AuthenticationProcessingFilter;
import ru.dayneko.authorization.utils.HttpRequestUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Фильтр проверки валидности Http запроса при попытке запроса на аутентификацию
 *
 * @author dayneko_si
 * @since 07.05.2019
 */
final public class HttpRequestJwtAuthenticationFilter extends AuthenticationProcessingFilter {
    private final AuthenticationSuccessHandler successHandler;
    private final AuthenticationFailureHandler failureHandler;
    private final ObjectMapper objectMapper;

    public HttpRequestJwtAuthenticationFilter(String defaultFilterProcessesUrl, AuthenticationSuccessHandler successHandler,
                                                 AuthenticationFailureHandler failureHandler, ObjectMapper mapper) {
        super(defaultFilterProcessesUrl);
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
        this.objectMapper = mapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("Http request filter");
        LoginParameters loginParameters = extractUserParametersFromRequest(request);

        checkHttpAuthRequestForCorrectness(request);
        checkUserCredentialsForCorrectness(loginParameters);

        return this.getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(loginParameters.getUsername(), loginParameters.getPassword()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        System.out.println("Http request Filter success");
        successHandler.onAuthenticationSuccess(request, response, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        failureHandler.onAuthenticationFailure(request, response, failed);
    }


    private LoginParameters extractUserParametersFromRequest(HttpServletRequest request) {
        try {
            return objectMapper.readValue(request.getReader(), LoginParameters.class);
        } catch (IOException e) {
            throw new AbsentLoginParametersException("One or both parameters for authentication is absent");
        }
    }

    private void checkUserCredentialsForCorrectness(LoginParameters loginParameters) {
        if (StringUtils.isBlank(loginParameters.getUsername()) || StringUtils.isBlank(loginParameters.getPassword())) {
            throw new AuthenticationServiceException("Username or Password not provided");
        }
    }

    private void checkHttpAuthRequestForCorrectness(HttpServletRequest request) {
        if (!HttpMethod.POST.name().equals(request.getMethod()) || !HttpRequestUtils.isContentTypeJson(request)) {
            if (logger.isDebugEnabled()) {
                logger.debug("Authentication method not supported. Request method: " + request.getMethod());
            }
            throw new AuthMethodNotSupportedException("Authentication method not supported");
        }
    }


    // todo вынести в отдельный класс
    @Data
    private static class LoginParameters {
        private String username;
        private String password;
    }
}
