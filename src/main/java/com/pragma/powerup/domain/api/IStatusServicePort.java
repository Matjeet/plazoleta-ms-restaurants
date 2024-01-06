package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.Status;

public interface IStatusServicePort {

    int getStatusId (String statusName);
}
