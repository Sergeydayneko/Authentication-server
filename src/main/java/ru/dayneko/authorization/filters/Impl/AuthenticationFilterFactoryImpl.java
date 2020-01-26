package ru.dayneko.authorization.filters.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;
import ru.dayneko.authorization.exceptions.WrongAuthenticationFilterException;
import ru.dayneko.authorization.filters.AuthenticationFilterFactory;
import ru.dayneko.authorization.filters.AuthenticationProcessingFilter;
import ru.dayneko.authorization.model.filter.AuthenticationFilter;
import ru.dayneko.authorization.utils.SkipPathsMatcher;
import ru.dayneko.authorization.utils.jwt.TokenExtractor;

import java.util.Arrays;


@RequiredArgsConstructor
@Service
public class AuthenticationFilterFactoryImpl implements AuthenticationFilterFactory {
    private final AuthenticationSuccessHandler successHandler;
    private final AuthenticationFailureHandler failureHandler;
    private final ObjectMapper mapper;
    private final TokenExtractor tokenExtractor;


    @Value("${server.authentication.url.login-entry-point}")
    private String LOGIN_ENTRY_POINT;

    @Value("${server.authentication.url.api-entry-point}")
    private String API_ENTRY_POINT;

    @Value("${server.authentication.url.base-auth-url}")
    private String BASE_AUTH_URL;

    @Value("${server.authentication.url.refresh-token-entry-point}")
    private String REFRESH_TOKEN_ENTRY_POINT;

    @Value("${server.authentication.url.raw-access-token-entry-point}")
    private String RAW_ACCESS_TOKEN_ENTRY_POINT;

    @Value("${server.authentication.url.swagger-entry-point}")
    private String SWAGGER_ENTRY_POINT;

    @Value("${server.authentication.url.swagger-resources}")
    private String SWAGGER_RESOURCES;

    @Value("${server.authentication.url.swagger-api-docs}")
    private String API_DOCS;

    @Value("${server.authentication.url.swagger-jars}")
    private String SWAGGER_JARS;

    @Value("${server.authentication.url.swagger-controller}")
    private String SWAGGER_CONTROLLER;


    public AuthenticationProcessingFilter getAuthenticationFilter(AuthenticationFilter filterType) {
        switch (filterType) {
            case HttpRequest:
                return new HttpRequestJwtAuthenticationFilter(API_ENTRY_POINT + LOGIN_ENTRY_POINT, successHandler, failureHandler, mapper);
            case UsernameAndPassword:
                return new JwtUsernameAndPasswordAuthenticationFilter(new SkipPathsMatcher(Arrays.asList(RAW_ACCESS_TOKEN_ENTRY_POINT, SWAGGER_ENTRY_POINT, REFRESH_TOKEN_ENTRY_POINT, SWAGGER_RESOURCES, API_DOCS, SWAGGER_JARS, SWAGGER_CONTROLLER), BASE_AUTH_URL), failureHandler, tokenExtractor);
            default:
                throw new WrongAuthenticationFilterException("Wrong fabric type. Type " + filterType + " doesnt exist!");
        }
    }
}
