package com.pragma.powerup.infrastructure.http.util;

import com.pragma.powerup.domain.spi.IHttpRequestContextHolderPersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HttpRequestContextHolder implements IHttpRequestContextHolderPersistencePort {

    private static final ThreadLocal<String> TOKEN = new ThreadLocal<>();
    @Override
    public void setToken(String token) {
        TOKEN.set(token);
    }

    @Override
    public String getToken() {
        return TOKEN.get();
    }

    @Override
    public void clearToken() {
        TOKEN.remove();
    }
}
