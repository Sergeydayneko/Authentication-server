package ru.dayneko.authorization.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ru.dayneko.authorization.model.token.UserContext;
import ru.dayneko.authorization.utils.jwt.TokenFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@Component
@RequiredArgsConstructor
final public class HttpRequestAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final ObjectMapper mapper;
    private final TokenFactory tokenFactory;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request
                                        , HttpServletResponse response
                                        , Authentication authentication) throws IOException {
        System.out.println("Http handler");

        val userContext = UserContext.create(authentication.getName(), null, new ArrayList<>());

        // todo подумать об обновлении
        val accessToken = tokenFactory.createAccessJwtToken(userContext);
        val refreshToken = tokenFactory.createRefreshToken(userContext);


        val tokenMap = new HashMap<String, String>() {
            {
                put("token", accessToken.getToken());
                put("refreshToken", refreshToken.getToken());
            }
        };

        mapper.writeValue(response.getWriter(), tokenMap);
        clearAuthenticationAttributes(request);
    }

    /**
     * Удаляет временные данные, связанные с проверкой подлинности
     * которые могли быть сохранены в сеансе во время процесса проверки подлинности.
     *
     * @param request запрос
     */
    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return;
        }

        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}
