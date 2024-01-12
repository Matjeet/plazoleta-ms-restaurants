package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.Dish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IDishPersistencePort {

    void saveDish(Dish dish);

    void updateDish(Dish dish);

    Dish getDish(int idDish);

    String changeStatus(int idDish, int idOwner);

    Page<Dish> getMenu(Pageable pageable, int idRestaurant, String category);
}
