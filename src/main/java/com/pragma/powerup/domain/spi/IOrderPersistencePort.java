package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.Order;

public interface IOrderPersistencePort {

    int saveOrder(Order order);

    Order getOrder(int id);
}
