package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IOrderServicePort;
import com.pragma.powerup.domain.model.Order;
import com.pragma.powerup.domain.spi.IOrderPersistencePort;
import org.springframework.data.domain.Page;

public class OrderUseCase implements IOrderServicePort {

    private final IOrderPersistencePort orderPersistencePort;
    public OrderUseCase(IOrderPersistencePort orderPersistencePort){
        this.orderPersistencePort = orderPersistencePort;
    }

    @Override
    public int saveOrder(Order order) {
        return orderPersistencePort.saveOrder(order);
    }

    @Override
    public Page<Order> getOrder(int id) {
        return orderPersistencePort.getOrder(id);
    }
}
