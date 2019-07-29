package ru.dayneko.authorization.filters;

import ru.dayneko.authorization.model.filter.AuthenticationFilter;

/**
 * Фабрика создания фильтров для авторизации внутри
 * спринг секьюрити
 *
 * @author dayneko_si
 * @since 07.06.2019
 */
public interface AuthenticationFilterFactory {
    AuthenticationProcessingFilter getAuthenticationFilter(AuthenticationFilter filterType);
}
