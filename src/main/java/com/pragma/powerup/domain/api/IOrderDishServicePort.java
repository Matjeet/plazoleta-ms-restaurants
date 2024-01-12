package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.OrderDish;

public interface IOrderDishServicePort {

    void saveOrderDish(OrderDish orderDish);
}
