package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.RegisterRestaurantDto;
import com.pragma.powerup.application.handler.IRestaurantHandler;
import com.pragma.powerup.application.mapper.IRestaurantRequestMapper;
import com.pragma.powerup.domain.model.Restaurant;
import com.pragma.powerup.domain.spi.IRestaurantPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantHandler implements IRestaurantHandler {

    private final IRestaurantPersistencePort  restaurantPersistencePort;
    private final IRestaurantRequestMapper restaurantRequestMapper;
    @Override
    public boolean saveRestaurant(RegisterRestaurantDto registerRestaurantDto) {
        Restaurant restaurant = restaurantRequestMapper.toRestaurant(registerRestaurantDto);
        return restaurantPersistencePort.saveRestaurant(restaurant);
    }
}
