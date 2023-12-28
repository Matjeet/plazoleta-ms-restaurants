package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.client.IOwnerFeignClient;
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
    private final IOwnerFeignClient ownerFeignClient;
    @Override
    public boolean saveRestaurant(RegisterRestaurantDto registerRestaurantDto) {
        if(ownerFeignClient.validateOwnerRole("Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYXRpdXMyOTA1M0BnbWFpbC5jb20iLCJleHAiOjE3MDM4MDU0NDEsInJvbGUiOlsiUk9MRV9hZG1pbmlzdHJhZG9yIl0sIm5hbWUiOiJtYXRpdXMyOTA1M0BnbWFpbC5jb20ifQ.HlJo1i7jLwPqm3l0wZ9ImRQHgturE9khTTDbQoqkiGA",registerRestaurantDto.getIdOwner())){
            Restaurant restaurant = restaurantRequestMapper.toRestaurant(registerRestaurantDto);
            return restaurantPersistencePort.saveRestaurant(restaurant);
        }
        return false;
    }
}
