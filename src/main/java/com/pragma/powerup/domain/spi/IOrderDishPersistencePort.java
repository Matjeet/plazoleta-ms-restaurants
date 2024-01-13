package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.OrderDish;

import java.util.List;

public interface IOrderDishPersistencePort {

    void saveOrderDish(List<OrderDish> orderDishes);
}
