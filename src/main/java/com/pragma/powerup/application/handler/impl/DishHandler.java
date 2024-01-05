package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.RegisterDishDto;
import com.pragma.powerup.application.dto.request.UpdateDishDto;
import com.pragma.powerup.application.handler.IDishHandler;
import com.pragma.powerup.application.mapper.ICategoryRequestMapper;
import com.pragma.powerup.application.mapper.IDishRequestMapper;
import com.pragma.powerup.domain.api.ICategoryServicePort;
import com.pragma.powerup.domain.api.IDishServicePort;
import com.pragma.powerup.domain.model.Category;
import com.pragma.powerup.domain.model.Dish;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DishHandler implements IDishHandler {

    private final IDishServicePort dishServicePort;
    private final ICategoryServicePort categoryServicePort;
    private final IDishRequestMapper dishRequestMapper;
    private final ICategoryRequestMapper categoryRequestMapper;
    @Override
    public void saveDish(RegisterDishDto registerDishDto) {

        Category savedCategory = categoryServicePort.saveCategory(
                categoryRequestMapper.toCategory(registerDishDto)
        );
        Dish dish = dishRequestMapper.toDish(registerDishDto);
        dish.setIdCategory(savedCategory.getId());
        dishServicePort.saveDish(dish);
    }

    @Override
    public void updateDish(UpdateDishDto updateDishDto, int id) {
        Dish dishToUpdate = dishServicePort.getDish(id);
        dishToUpdate.setPrice(updateDishDto.getPrice());
        dishToUpdate.setDescription(updateDishDto.getDescription());
        dishServicePort.updateDish(dishToUpdate);
    }
}
