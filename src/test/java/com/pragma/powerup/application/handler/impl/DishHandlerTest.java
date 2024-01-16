package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.CategoryDto;
import com.pragma.powerup.application.dto.StatusDto;
import com.pragma.powerup.application.dto.request.RegisterCategoryDto;
import com.pragma.powerup.application.dto.request.RegisterDishDto;
import com.pragma.powerup.application.dto.request.UpdateDishDto;
import com.pragma.powerup.application.dto.response.DishPageResponseDto;
import com.pragma.powerup.application.mapper.request.ICategoryRequestMapper;
import com.pragma.powerup.application.mapper.request.IDishRequestMapper;
import com.pragma.powerup.application.mapper.response.IDishResponseMapper;
import com.pragma.powerup.domain.api.ICategoryServicePort;
import com.pragma.powerup.domain.api.IDishServicePort;
import com.pragma.powerup.domain.api.IStatusServicePort;
import com.pragma.powerup.domain.model.Category;
import com.pragma.powerup.domain.model.Dish;
import com.pragma.powerup.domain.model.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DishHandlerTest {

    private Dish dish;
    private Category category;
    private Status status;
    private RegisterDishDto registerDishDto;
    private RegisterCategoryDto registerCategoryDto;
    private UpdateDishDto updateDishDto;
    private DishPageResponseDto dishPageResponseDto;
    private CategoryDto categoryDto;
    private StatusDto statusDto;

    @Mock
    private IDishServicePort dishServicePort;
    @Mock
    private ICategoryServicePort categoryServicePort;
    @Mock
    private IDishRequestMapper dishRequestMapper;
    @Mock
    private ICategoryRequestMapper categoryRequestMapper;
    @Mock
    private IStatusServicePort statusServicePort;
    @Mock
    private IDishResponseMapper dishResponseMapper;
    @InjectMocks
    private DishHandler dishHandler;

    @BeforeEach
    void setUp() {
        dish = new Dish();
        category = new Category();
        status = new Status();
        registerDishDto = new RegisterDishDto();
        registerCategoryDto = new RegisterCategoryDto();
        updateDishDto = new UpdateDishDto();
        dishPageResponseDto = new DishPageResponseDto();
        categoryDto = new CategoryDto();
        statusDto = new StatusDto();

        dish.setName("Pizza");
        dish.setPrice(134568);
        dish.setDescription("Pizza con piña");
        dish.setUrlImage("https://pizzaConPiniaImagen.com");
        dish.setIdCategory(1);
        dish.setIdRestaurant(1);

        category.setId(1);
        category.setName("italiana");
    }

    @Test
    void saveDish() {

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

    @Test
    void getMenu() {

        Pageable pageable = PageRequest.of(1,1);
        Page<Dish> dishPage = Page.empty(PageRequest.of(1,2));
        Page<DishPageResponseDto> dishPageResponseDtos = Page.empty(PageRequest.of(1,2));

        status.setId(1);
        status.setName("habilitado");

        categoryDto.setName("italiana");

        statusDto.setName("habilitado");

        dishPageResponseDto.setName("pasta");
        dishPageResponseDto.setPrice(12000);
        dishPageResponseDto.setDescription("pasta con salsa");
        dishPageResponseDto.setUrlImage("https://pasta.com");
        dishPageResponseDto.setCategory(categoryDto);
        dishPageResponseDto.setStatus(statusDto);

        when(dishServicePort.getMenu(pageable,1,null)).thenReturn(dishPage);

        dishHandler.getMenu(pageable,1,null);

        verify(dishServicePort, times(1)).getMenu(pageable,1,null);
        assertEquals(dishPageResponseDtos, dishHandler.getMenu(pageable,1,null));

    }
}