package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IOrderServicePort {

    int saveOrder(Order order);

    Page<Order> getOrderByStatusAndRestaurant(Pageable pageable, int idStatus, int idRestaurant);

    void orderInProcess(int idEmployee, int idOrder);

    Order orderReady(int idEmployee, int idOrder);

    void saveSecurityCode(int securityCode, int idOrder);

    void orderDelivered(int securityCode, int idOrder, int idStatus);
}
