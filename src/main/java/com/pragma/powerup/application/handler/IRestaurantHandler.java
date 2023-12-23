package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.RegisterRestaurantDto;

public interface IRestaurantHandler {

    boolean saveRestaurant(RegisterRestaurantDto registerRestaurantDto);
}
