package com.ethangerty.records;

import java.util.UUID;

public record Challengers(UUID challenger, UUID target, long expiresAtMillis) {
    public boolean isExpired() {
        return System.currentTimeMillis() > expiresAtMillis;
    }
}