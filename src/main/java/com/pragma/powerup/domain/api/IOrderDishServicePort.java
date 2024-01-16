package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.OrderDish;

import java.util.List;

public interface IOrderDishServicePort {

    void saveOrderDish(List<OrderDish> orderDishes);
}
