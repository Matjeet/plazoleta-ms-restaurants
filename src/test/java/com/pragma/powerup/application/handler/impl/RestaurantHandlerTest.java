package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.client.IUsersFeignClient;
import com.pragma.powerup.application.dto.request.RegisterRestaurantDto;
import com.pragma.powerup.application.mapper.request.IRestaurantRequestMapper;
import com.pragma.powerup.domain.api.IHttpRequestContextHolderServicePort;
import com.pragma.powerup.domain.api.IRestaurantServicePort;
import com.pragma.powerup.domain.model.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class RestaurantHandlerTest {

    private RegisterRestaurantDto registerRestaurantDto;

    private Restaurant restaurant;

    @Mock
    private IUsersFeignClient usersFeignClient;
    @Mock
    private IRestaurantRequestMapper restaurantRequestMapper;
    @Mock
    private IRestaurantServicePort restaurantServicePort;
    @Mock
    private IHttpRequestContextHolderServicePort httpRequestContextHolderServicePort;

    @InjectMocks
    private RestaurantHandler restaurantHandler;

    @BeforeEach
    void setUp() {
        registerRestaurantDto = new RegisterRestaurantDto();
        restaurant = new Restaurant();
    }
    @Test
    void saveRestaurantSuccess() {
        registerRestaurantDto.setName("");
        registerRestaurantDto.setNit("");
        registerRestaurantDto.setAddress("");
        registerRestaurantDto.setPhone("");
        registerRestaurantDto.setLogoUrl("");
        registerRestaurantDto.setIdOwner(1);

        restaurant.setId(1);
        restaurant.setName("");
        restaurant.setNit("");
        restaurant.setAddress("");
        restaurant.setPhone("");
        restaurant.setLogoUrl("");
        restaurant.setIdOwner(1);

        when(httpRequestContextHolderServicePort.getToken()).thenReturn("");
        when(usersFeignClient.validateOwnerRole(anyString(), anyInt())).thenReturn(true);
        when(restaurantRequestMapper.toRestaurant(registerRestaurantDto)).thenReturn(restaurant);
        doNothing().when(restaurantServicePort).saveRestaurant(restaurant);

        boolean result = restaurantHandler.saveRestaurant(registerRestaurantDto);

        assertTrue(result);
    }

    @Test
    void saveRestaurantFailed(){

        when(httpRequestContextHolderServicePort.getToken()).thenReturn("");
        when(usersFeignClient.validateOwnerRole(anyString(), anyInt())).thenReturn(false);

        boolean result = restaurantHandler.saveRestaurant(registerRestaurantDto);

        assertFalse(result);
    }
}