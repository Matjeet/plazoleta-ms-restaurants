package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.client.IOwnerFeignClient;
import com.pragma.powerup.application.dto.request.RegisterRestaurantDto;
import com.pragma.powerup.application.mapper.IRestaurantRequestMapper;
import com.pragma.powerup.domain.api.IRestaurantServicePort;
import com.pragma.powerup.domain.model.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static reactor.core.publisher.Mono.when;

@ExtendWith(MockitoExtension.class)
class RestaurantHandlerTest {

    private RegisterRestaurantDto registerRestaurantDto;

    @Mock
    private IOwnerFeignClient ownerFeignClient;

    @Mock
    private IRestaurantRequestMapper restaurantRequestMapper;

    @Mock
    private IRestaurantServicePort restaurantServicePort;

    @InjectMocks
    private RestaurantHandler restaurantHandler;

    @BeforeEach
    void setUp() {
        registerRestaurantDto = new RegisterRestaurantDto();
    }
    @Test
    void saveRestaurant() {
        registerRestaurantDto.setName("D1");
        registerRestaurantDto.setNit("123456795");
        registerRestaurantDto.setAddress("Cra 13 sur #54-678");
        registerRestaurantDto.setPhone("+573226749122");
        registerRestaurantDto.setLogoUrl("https://logo.com");
        registerRestaurantDto.setIdOwner(1);
        
    }
}