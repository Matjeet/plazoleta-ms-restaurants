package com.pragma.powerup.domain.api;

public interface IHttpRequestContextHolderServicePort {

    void setToken(String token);
    String getToken();
    void clearToken();
}
