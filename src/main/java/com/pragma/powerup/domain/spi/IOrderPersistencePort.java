package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IOrderPersistencePort {

    int saveOrder(Order order);

    Page<Order> getOrderByStatusAndRestaurant(Pageable pageable, int idStatus, int idRestaurant);

    void orderInProcess(int idEmployee, int idOrder);

    void orderReady(int idEmployee, int idOrder);
}
