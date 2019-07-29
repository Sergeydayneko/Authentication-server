package ru.dayneko.authorization.services.Impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;
import ru.dayneko.authorization.services.LoginAttemptService;

import javax.validation.constraints.NotNull;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public final class LoginAttemptServiceIml implements LoginAttemptService {
    private final int MAX_ATTEMPT = 15;
    private LoadingCache<String, Integer> attemptsCache;

    public LoginAttemptServiceIml() {
        super();
        attemptsCache = CacheBuilder.newBuilder()
                .expireAfterWrite(1, TimeUnit.HOURS)
                .concurrencyLevel(4)
                .build(new CacheLoader<String, Integer>() {
            public Integer load(@NotNull String key) {
                return 0;
            }
        });
    }

    public void loginSucceeded(String key) {
        attemptsCache.invalidate(key);
    }

    public void loginFailed(String key) {
        int attempts;
        try {
            attempts = attemptsCache.get(key);
        } catch (ExecutionException e) {
            attempts = 0;
        }
        attempts++;
        attemptsCache.put(key, attempts);
    }

    public boolean isBlocked(String key) {
        try {
            return attemptsCache.get(key) >= MAX_ATTEMPT;
        } catch (ExecutionException e) {
            return false;
        }
    }
}
