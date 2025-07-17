package com.finemeet.authservice.jwt;

import com.finemeet.authservice.exception.jwt.JwtTokenBlacklistedException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtBlacklistValidator {
    private final Set<String> blacklistedTokens = Collections.synchronizedSet(new HashSet<>());

    public void addToBlacklist(String token) {
        blacklistedTokens.add(token);
    }

    public void validate(String token) {
        if (blacklistedTokens.contains(token)) {
            throw new JwtTokenBlacklistedException();
        }
    }
}
