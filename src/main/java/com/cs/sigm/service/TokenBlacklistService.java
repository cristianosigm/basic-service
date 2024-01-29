package com.cs.sigm.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class TokenBlacklistService {

    // TODO: Use the database to persist the tokens in case of a reboot, while keeping them in cache

    // TODO: create a job that remove expired tokens

    private final Set<String> blacklistedTokens = new HashSet<>();

    public boolean isBlacklisted(String token) {
        return blacklistedTokens.contains(token);
    }

    public void blacklistToken(String token) {
        blacklistedTokens.add(token);
    }

}
