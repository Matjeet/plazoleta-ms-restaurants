package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.RegisterCategoryDto;
import com.pragma.powerup.application.dto.request.RegisterDishDto;
import com.pragma.powerup.application.dto.request.UpdateDishDto;
import com.pragma.powerup.application.mapper.ICategoryRequestMapper;
import com.pragma.powerup.application.mapper.IDishRequestMapper;
import com.pragma.powerup.domain.api.ICategoryServicePort;
import com.pragma.powerup.domain.api.IDishServicePort;
import com.pragma.powerup.domain.model.Category;
import com.pragma.powerup.domain.model.Dish;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DishHandlerTest {

    private Dish dish;
    private Category category;
    private RegisterDishDto registerDishDto;
    private RegisterCategoryDto registerCategoryDto;
    private UpdateDishDto updateDishDto;

    @Mock
    private IDishServicePort dishServicePort;
    @Mock
    private ICategoryServicePort categoryServicePort;
    @Mock
    private IDishRequestMapper dishRequestMapper;
    @Mock
    private ICategoryRequestMapper categoryRequestMapper;
    @InjectMocks
    private DishHandler dishHandler;

    @BeforeEach
    void setUp() {
        dish = new Dish();
        category = new Category();
        registerDishDto = new RegisterDishDto();
        registerCategoryDto = new RegisterCategoryDto();
        updateDishDto = new UpdateDishDto();

        dish.setName("Pizza");
        dish.setPrice(134568);
        dish.setDescription("Pizza con piña");
        dish.setUrlImage("https://pizzaConPiniaImagen.com");
        dish.setIdCategory(1);
        dish.setIdRestaurant(1);
    }

    @Test
    void saveDish() {
        category.setId(1);
        category.setName("italiana");

        registerCategoryDto.setName("italiana");

        registerDishDto.setName("Pizza");
        registerDishDto.setPrice(134568);
        registerDishDto.setDescription("Pizza con piña");
        registerDishDto.setUrlImage("https://pizzaConPiniaImagen.com");
        registerDishDto.setCategory(registerCategoryDto);
        registerDishDto.setIdRestaurant(1);

        when(categoryRequestMapper.toCategory(registerDishDto)).thenReturn(category);
        when(categoryServicePort.saveCategory(category)).thenReturn(category);
        when(dishRequestMapper.toDish(registerDishDto)).thenReturn(dish);
        doNothing().when(dishServicePort).saveDish(dish);

        dishHandler.saveDish(registerDishDto);

        verify(categoryRequestMapper, times(1)).toCategory(registerDishDto);
        verify(categoryServicePort, times(1)).saveCategory(category);
        verify(dishRequestMapper,times(1)).toDish(registerDishDto);
        verify(dishServicePort, times(1)).saveDish(dish);
    }

    @Test
    void updateDish() {
        updateDishDto.setPrice(12000);
        updateDishDto.setDescription("pizza con piña");

        when(dishServicePort.getDish(anyInt())).thenReturn(dish);
        doNothing().when(dishServicePort).updateDish(dish);

        dishHandler.updateDish(updateDishDto, 1);

        verify(dishServicePort,times(1)).getDish(anyInt());
        verify(dishServicePort,times(1)).updateDish(dish);
    }
}