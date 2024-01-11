package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.Status;

public interface IStatusPersistencePort {

    int getStatusId (String name);

    Status getStatus (int id);
}
