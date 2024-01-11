package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRestaurantServicePort {

    void saveRestaurant(Restaurant restaurant);

    Page<Restaurant> getRestaurants(Pageable pageable);
}
