package com.example.finanzas.security;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class TokenBlacklistService {
    private final Map<String, Instant> blacklist = new ConcurrentHashMap<>();

    public void blacklist(String token, Instant expiration) {
        cleanup();
        blacklist.put(token, expiration);
    }

    public boolean isBlacklisted(String token) {
        cleanup();
        Instant expiry = blacklist.get(token);
        return expiry != null && expiry.isAfter(Instant.now());
    }

    private void cleanup() {
        Instant now = Instant.now();
        blacklist.entrySet().removeIf(entry -> entry.getValue().isBefore(now));
    }
}
