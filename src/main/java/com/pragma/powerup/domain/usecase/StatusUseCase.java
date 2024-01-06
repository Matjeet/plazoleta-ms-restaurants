package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IStatusServicePort;
import com.pragma.powerup.domain.model.Status;
import com.pragma.powerup.domain.spi.IStatusPersistencePort;

public class StatusUseCase implements IStatusServicePort {

    private final IStatusPersistencePort statusPersistencePort;

    public StatusUseCase(IStatusPersistencePort statusPersistencePort){
        this.statusPersistencePort = statusPersistencePort;
    }
    @Override
    public int getStatusId(String statusName) {
        return statusPersistencePort.getStatusId(statusName);
    }
}
