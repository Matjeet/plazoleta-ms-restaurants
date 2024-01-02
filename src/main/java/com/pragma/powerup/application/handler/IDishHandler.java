package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.RegisterDishDto;

public interface IDishHandler {

    void saveDish(RegisterDishDto registerDishDto);
}
