package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IOrderDishServicePort;
import com.pragma.powerup.domain.model.OrderDish;
import com.pragma.powerup.domain.spi.IOrderDishPersistencePort;

import java.util.List;

public class OrderDishUseCase implements IOrderDishServicePort {

    private final IOrderDishPersistencePort orderDishPersistencePort;

    public OrderDishUseCase(IOrderDishPersistencePort orderDishPersistencePort){
        this.orderDishPersistencePort = orderDishPersistencePort;
    }
    @Override
    public void saveOrderDish(List<OrderDish> orderDishes) {
        orderDishPersistencePort.saveOrderDish(orderDishes);
    }
}
