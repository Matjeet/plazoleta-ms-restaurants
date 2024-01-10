package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.Restaurant;
import com.pragma.powerup.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup.infrastructure.out.jpa.entity.RestaurantEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IRestaurantEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IRestaurantRepository;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class RestaurantJpaAdapter implements IRestaurantPersistencePort {

    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;

    @Override
    public boolean saveRestaurant(Restaurant restaurant) {
        try{
            restaurantRepository.save(restaurantEntityMapper.toEntity(restaurant));
        }
        catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public Page<Restaurant> getRestaurants(Pageable pageable) {

        Page<RestaurantEntity> restaurantEntities;

        try{
            restaurantEntities = restaurantRepository.findAll(pageable);
        }
        catch (Exception e){
            return null;
        }

        return restaurantEntityMapper.toRestaurantsPage(restaurantEntities);
    }
}
