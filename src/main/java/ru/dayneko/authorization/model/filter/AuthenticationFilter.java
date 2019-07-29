package ru.dayneko.authorization.model.filter;

import lombok.ToString;

@ToString
public enum AuthenticationFilter {
    UsernameAndPassword, HttpRequest
}
