package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.RegisterDishDto;
import com.pragma.powerup.application.dto.request.UpdateDishDto;
import com.pragma.powerup.application.dto.response.DishPageResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IDishHandler {

    void saveDish(RegisterDishDto registerDishDto);

    void updateDish(UpdateDishDto updateDishDto, int id);

    String changeStatus(int idDish, int idOwner);

    Page<DishPageResponseDto> getMenu (Pageable pageable, int idRestaurant, String categoryDish);
}
