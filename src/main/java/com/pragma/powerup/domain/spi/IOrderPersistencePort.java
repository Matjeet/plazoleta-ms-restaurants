package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.Order;
import org.springframework.data.domain.Page;

public interface IOrderPersistencePort {

    int saveOrder(Order order);

    Page<Order> getOrder(int id);
}
