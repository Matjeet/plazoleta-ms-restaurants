package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.Order;
import org.springframework.data.domain.Page;

public interface IOrderServicePort {

    int saveOrder(Order order);

    Page<Order> getOrder(int id);
}
