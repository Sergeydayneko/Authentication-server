package ru.dayneko.authorization.utils.jwt;

/**
 * Экстрактор токена из заголовка
 *
 * @author dayneko_si
 * @since 13.05.2019
 */
public interface TokenExtractor {
    String extractTokenFromHeader(String payload);
}
