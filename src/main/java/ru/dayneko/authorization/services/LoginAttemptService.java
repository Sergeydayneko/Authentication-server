package ru.dayneko.authorization.services;

public interface LoginAttemptService {
    void loginSucceeded(String key);
    void loginFailed(String key);
    boolean isBlocked(String key);
}
