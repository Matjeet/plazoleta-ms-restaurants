package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IRestaurantServicePort;
import com.pragma.powerup.domain.exception.ParameterNotValidException;
import com.pragma.powerup.domain.model.Restaurant;
import com.pragma.powerup.domain.spi.IRestaurantPersistencePort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.regex.Pattern;

public class RestaurantUseCase implements IRestaurantServicePort {

    private final IRestaurantPersistencePort restaurantPersistencePort;

    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort){
        this.restaurantPersistencePort = restaurantPersistencePort;
    }

    @Override
    public void saveRestaurant(Restaurant restaurant) {
        boolean validPhoneNumber = Pattern.compile(
                        "^\\+\\d{12}$",
                        Pattern.CASE_INSENSITIVE
                )
                .matcher(restaurant.getPhone())
                .find();

        boolean validNit = Pattern.compile(
                "^\\d+$",
                Pattern.CASE_INSENSITIVE
                )
                .matcher(restaurant.getNit())
                .find();

        boolean validName = Pattern.compile(
                ".*[a-zA-Z].*",
                Pattern.CASE_INSENSITIVE
                )
                .matcher(restaurant.getName())
                .find();

        if(validPhoneNumber && validNit && validName){
            restaurantPersistencePort.saveRestaurant(restaurant);
        }
        else {
            throw new ParameterNotValidException();
        }

    }

    @Override
    public Page<Restaurant> getRestaurants(Pageable pageable) {
        return restaurantPersistencePort.getRestaurants(pageable);
    }
}
