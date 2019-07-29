package ru.dayneko.authorization.common;

/**
 * Объект для получения области видимости
 *
 * @author dayneko_si
 * @since 22.05.2019
 */
public enum Scopes {
    REFRESH_TOKEN;

    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}
