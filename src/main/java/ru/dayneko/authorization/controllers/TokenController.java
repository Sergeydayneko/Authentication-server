package ru.dayneko.authorization.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.dayneko.authorization.common.ErrorCode;
import ru.dayneko.authorization.common.ErrorResponse;
import ru.dayneko.authorization.config.JwtConfig;
import ru.dayneko.authorization.config.WebSecurityConfig;
import ru.dayneko.authorization.exceptions.InvalidJwtTokenException;
import ru.dayneko.authorization.model.token.JwtToken;
import ru.dayneko.authorization.model.token.RawAccessJwtToken;
import ru.dayneko.authorization.model.token.UserContext;
import ru.dayneko.authorization.services.UserService;
import ru.dayneko.authorization.utils.jwt.TokenExtractor;
import ru.dayneko.authorization.utils.jwt.TokenFactory;
import ru.dayneko.authorization.utils.jwt.TokenVerifier;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Контроллер для работы с токенами
 *
 * @author dayneko_si
 * @since 24.05.2019
 */
@RequestMapping("/v1")
@RestController
@RequiredArgsConstructor
@Api(tags = "Контроллер для работы с токенами")
final public class TokenController {
    private final TokenExtractor tokenExtractor;
    private final JwtConfig jwtConfig;
    private final TokenVerifier tokenVerifier;
    private final UserService userService;
    private final TokenFactory tokenFactory;

    @ApiOperation("Получение нового токена")
    @GetMapping("/token")
    public ResponseEntity getRawAccessToken(@RequestParam String login, @RequestParam String password) throws IOException {
        val user    = userService.getUserByLogin(login).orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден: " + login));

        if (userService.checkUserCredentials(user, password)) {
            // todo доделать granted authorities, когда появятся роли на пользователей
            val userContext = UserContext.create(user.getLogin(), user.getFio(), new ArrayList<>());

            return responseEntityWithTokens(userContext);

        }

        return ResponseEntity.status(401).body("WRONG CREDENTIALS");
    }

    @ApiOperation("Получение нового RawJWT через RefreshJWT")
    @GetMapping("/refresh")
    public ResponseEntity getAccessTokenByRefreshToken(HttpServletRequest request, HttpServletResponse response) {
        try {
            val refreshJwtToken = getRefreshJwtTokenFromRequest(request);
            checkRefreshJwtTokenValidity(refreshJwtToken);

            val subject = refreshJwtToken.getSubject();
            val user    = userService.getUserByLogin(subject).orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден: " + subject));

            // todo доделать granted authorities, когда появятся роли на пользователей
            val userContext = UserContext.create(user.getLogin(), user.getFio(), new ArrayList<>());

            return responseEntityWithTokens(userContext);
        } catch (InvalidJwtTokenException | AuthenticationException ijwte) {
            return new ResponseEntity<>(ErrorResponse.of(ijwte.getMessage(), ErrorCode.AUTHENTICATION, HttpStatus.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(ErrorResponse.of("Критическая ошибка сервера", ErrorCode.GLOBAL, HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private JwtToken getRefreshJwtTokenFromRequest(HttpServletRequest request) {
        val tokenPayload = tokenExtractor.extractTokenFromHeader(request.getHeader(WebSecurityConfig.JWT_TOKEN_HEADER_PARAM));

        return JwtToken.createRefreshTokenFromRawAccessToken(new RawAccessJwtToken(tokenPayload), jwtConfig.getSecret())
                .orElseThrow(InvalidJwtTokenException::new);
    }

    private void checkRefreshJwtTokenValidity(JwtToken refreshJwtToken) {
        boolean isTokenValid = tokenVerifier.verifyClaims(refreshJwtToken.getClaims());
        if (!isTokenValid) throw new InvalidJwtTokenException("Wrong claims when was checking refresh token!");
    }

    private ResponseEntity<Map<String, String>> responseEntityWithTokens(UserContext userContext) throws IOException {
        val accessToken = tokenFactory.createAccessJwtToken(userContext);
        val refreshToken = tokenFactory.createRefreshToken(userContext);

        val tokenMap = new HashMap<String, String>() {
            {
                put("token", accessToken.getToken());
                put("refreshToken", refreshToken.getToken());

            }
        };

        return new ResponseEntity<>(tokenMap, HttpStatus.OK);

    }
}
