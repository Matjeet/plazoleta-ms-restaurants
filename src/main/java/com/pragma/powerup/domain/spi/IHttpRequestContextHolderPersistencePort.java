package com.pragma.powerup.domain.spi;

public interface IHttpRequestContextHolderPersistencePort {

    void setToken(String token);
    String getToken();
    void clearToken();
}
