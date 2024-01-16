package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IOrderServicePort {

    int saveOrder(Order order);

    Page<Order> getOrderByStatus(Pageable pageable, int idStatus, int idRestaurant);
}
