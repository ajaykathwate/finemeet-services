package com.finemeet.authservice.token;


import com.finemeet.authservice.dto.UserRegistrationRequest;
import com.finemeet.authservice.exception.token.IncorrectTokenException;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TokenCache {
    private final Cache<String, UserRegistrationRequest> tokenCache;

    public TokenCache(@Value("${temporary-cache.time.token}") Integer expireTime) {
        this.tokenCache = CacheBuilder.newBuilder()
                .expireAfterWrite(expireTime, TimeUnit.MINUTES)
                .build();
    }

    public void addToken(String tokenKey, UserRegistrationRequest request) {
        tokenCache.put(tokenKey, request);
    }

    public UserRegistrationRequest getToken(String tokenKey) {
        UserRegistrationRequest userRegistrationRequest = tokenCache.getIfPresent(tokenKey);
        if (userRegistrationRequest == null) {
            throw new IncorrectTokenException();
        }
        return userRegistrationRequest;
    }

    public void removeToken(String tokenKey) {
        tokenCache.invalidate(tokenKey);
    }
}

