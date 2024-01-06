package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.Dish;

public interface IDishPersistencePort {

    void saveDish(Dish dish);

    void updateDish(Dish dish);

    Dish getDish(int idDish);

    String changeStatus(int idDish, int idOwner);
}
