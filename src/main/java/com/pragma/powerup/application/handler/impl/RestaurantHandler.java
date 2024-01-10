package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.client.IOwnerFeignClient;
import com.pragma.powerup.application.dto.request.RegisterRestaurantDto;
import com.pragma.powerup.application.dto.response.RestaurantsPageResponseDto;
import com.pragma.powerup.application.handler.IRestaurantHandler;
import com.pragma.powerup.application.mapper.request.IRestaurantRequestMapper;
import com.pragma.powerup.application.mapper.response.IRestaurantResponseMapper;
import com.pragma.powerup.domain.api.IRestaurantServicePort;
import com.pragma.powerup.domain.model.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantHandler implements IRestaurantHandler {

    private final IRestaurantServicePort restaurantServicePort;
    private final IRestaurantRequestMapper restaurantRequestMapper;
    private final IRestaurantResponseMapper restaurantResponseMapper;
    private final IOwnerFeignClient ownerFeignClient;
    @Override
    public boolean saveRestaurant(RegisterRestaurantDto registerRestaurantDto) {
        if(ownerFeignClient.validateOwnerRole(
                "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYXRpdXMyOTA1M0BnbWFpbC5jb20iLCJleHAiOjE3MDM4ODc3ODYsInJvbGUiOlsiUk9MRV9hZG1pbmlzdHJhZG9yIl0sIm5hbWUiOiJtYXRpdXMyOTA1M0BnbWFpbC5jb20ifQ.awmJ7dNHjTvesKpiME5mKUfp6BkU61pSGwghGUIx434",
                registerRestaurantDto.getIdOwner())){
            Restaurant restaurant = restaurantRequestMapper.toRestaurant(registerRestaurantDto);
            restaurantServicePort.saveRestaurant(restaurant);
            return true;
        }
        return false;
    }

    @Override
    public Page<RestaurantsPageResponseDto> getRestaurants(Pageable pageable) {
        Page<Restaurant> restaurants = restaurantServicePort.getRestaurants(pageable);
        return restaurantResponseMapper.toRestaurantsPageDto(restaurants);
    }
}
