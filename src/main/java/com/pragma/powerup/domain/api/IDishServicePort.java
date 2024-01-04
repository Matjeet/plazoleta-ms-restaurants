package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.Dish;

public interface IDishServicePort {

    void saveDish(Dish dish);

    void updateDish(Dish dish);

    Dish getDish(int idDish);
}
