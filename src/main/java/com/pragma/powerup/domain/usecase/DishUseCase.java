package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IDishServicePort;
import com.pragma.powerup.domain.exception.InvalidPriceException;
import com.pragma.powerup.domain.model.Dish;
import com.pragma.powerup.domain.spi.IDishPersistencePort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class DishUseCase implements IDishServicePort {

    private final IDishPersistencePort dishPersistencePort;
    public DishUseCase(IDishPersistencePort dishPersistencePort) {
        this.dishPersistencePort = dishPersistencePort;
    }
    @Override
    public void saveDish(Dish dish) {

        if(dish.getPrice() > 0) {
            dishPersistencePort.saveDish(dish);
        }
        else {
            throw new InvalidPriceException();
        }

    }

    @Override
    public void updateDish(Dish dish) {
        dishPersistencePort.updateDish(dish);
    }

    @Override
    public Dish getDish(int idDish) {
        return dishPersistencePort.getDish(idDish);
    }

    @Override
    public String changeStatus(int idDish, int idOwner) {
        return dishPersistencePort.changeStatus(idDish, idOwner);
    }

    @Override
    public Page<Dish> getMenu(Pageable pageable, int idRestaurant, String category) {
        return dishPersistencePort.getMenu(pageable, idRestaurant, category);
    }
}
