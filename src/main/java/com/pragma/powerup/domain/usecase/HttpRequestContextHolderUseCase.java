package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IHttpRequestContextHolderServicePort;
import com.pragma.powerup.domain.spi.IHttpRequestContextHolderPersistencePort;

public class HttpRequestContextHolderUseCase implements IHttpRequestContextHolderServicePort {

    private final IHttpRequestContextHolderPersistencePort httpRequestContextHolderPersistencePort;

    public HttpRequestContextHolderUseCase(
            IHttpRequestContextHolderPersistencePort httpRequestContextHolderPersistencePort
    ){
        this.httpRequestContextHolderPersistencePort = httpRequestContextHolderPersistencePort;
    }

    @Override
    public void setToken(String token) {
        httpRequestContextHolderPersistencePort.setToken(token);
    }

    @Override
    public String getToken() {
        return httpRequestContextHolderPersistencePort.getToken();
    }

    @Override
    public void clearToken() {
        httpRequestContextHolderPersistencePort.clearToken();
    }
}
