package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.RegisterDishDto;
import com.pragma.powerup.application.dto.request.UpdateDishDto;

public interface IDishHandler {

    void saveDish(RegisterDishDto registerDishDto);

    void updateDish(UpdateDishDto updateDishDto, int id);

    String changeStatus(int idDish, int idOwner);
}
