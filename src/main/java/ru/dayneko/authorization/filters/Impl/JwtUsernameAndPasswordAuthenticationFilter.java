package ru.dayneko.authorization.filters.Impl;

import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;
import ru.dayneko.authorization.config.WebSecurityConfig;
import ru.dayneko.authorization.filters.AuthenticationProcessingFilter;
import ru.dayneko.authorization.model.token.JwtAuthenticationToken;
import ru.dayneko.authorization.model.token.RawAccessJwtToken;
import ru.dayneko.authorization.utils.jwt.TokenExtractor;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Фильтр для проверки пользователя и пароля
 *
 * @author dayneko_si
 * @since 23.04.2019
 */
final public class JwtUsernameAndPasswordAuthenticationFilter extends AuthenticationProcessingFilter {
    private Logger logger = LoggerFactory.getLogger(JwtUsernameAndPasswordAuthenticationFilter.class);
    private final AuthenticationFailureHandler failureHandler;
    private final TokenExtractor tokenExtractor;

    JwtUsernameAndPasswordAuthenticationFilter(RequestMatcher pathsToSkip
            , AuthenticationFailureHandler failureHandler
            , TokenExtractor tokenExtractor) {
        super(pathsToSkip);
        this.failureHandler = failureHandler;
        this.tokenExtractor = tokenExtractor;
    }

    /**
     * Метод для осуществления аутентификации пользователя
     *
     * @param request  - текущий запроса
     * @param response - текущий объект ответа
     * @return объект аутентификация с логином, паролем и правами(пустой лист заполнится в другом сервисе)
     * @throws AuthenticationException ошибка при выявлении неверного токена
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        logger.info("Username and password filter");

        val tokenPayload = request.getHeader(WebSecurityConfig.JWT_TOKEN_HEADER_PARAM);
        val token = new RawAccessJwtToken(tokenExtractor.extractTokenFromHeader(tokenPayload));

        return getAuthenticationManager().authenticate(new JwtAuthenticationToken(token));
    }

    /**
     * Метод инициализация токена для пользователя
     * в случае удачной аутентификации
     *
     * @param request    - текущий запроса
     * @param response   - текущий объект ответа
     * @param chain      - объект цепочки фильтров
     * @param authResult - объект авторизациии
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request
                                            , HttpServletResponse response
                                            , FilterChain chain
                                            , Authentication authResult) throws IOException, ServletException {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);
        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request
                                                , HttpServletResponse response
                                                , AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        failureHandler.onAuthenticationFailure(request, response, failed);
    }

}
