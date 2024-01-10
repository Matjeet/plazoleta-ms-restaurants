package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.RegisterRestaurantDto;
import com.pragma.powerup.application.dto.response.RestaurantsPageResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRestaurantHandler {

    boolean saveRestaurant(RegisterRestaurantDto registerRestaurantDto);

    Page<RestaurantsPageResponseDto> getRestaurants(Pageable pageable);
}
