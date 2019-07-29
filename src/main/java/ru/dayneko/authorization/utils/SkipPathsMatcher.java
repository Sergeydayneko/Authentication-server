package ru.dayneko.authorization.utils;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Класс для реализации списка URL'ов, которые можно
 * будет пропускать при прохождении фильтров
 * требующих авторизацию
 *
 * @author dayneko_si
 * @since 21.05.2019
 */
public class SkipPathsMatcher implements RequestMatcher {
    /** Списос запросов**/
    private OrRequestMatcher listOfRequestMatchers;
    /** Обработчик запросов **/
    private RequestMatcher baseRequestMatcher;

    public SkipPathsMatcher(List<String> pathsToSkip, String baseProcessPath) {
        listOfRequestMatchers = new OrRequestMatcher(pathsToSkip.stream()
                .filter(Objects::nonNull)
                .map(AntPathRequestMatcher::new)
                .collect(Collectors.toCollection(ArrayList::new)));
        baseRequestMatcher = new AntPathRequestMatcher(baseProcessPath);
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        return !listOfRequestMatchers.matches(request) && (baseRequestMatcher.matches(request));
     }
}
