package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IOrderServicePort;
import com.pragma.powerup.domain.model.Order;
import com.pragma.powerup.domain.spi.IOrderPersistencePort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
    public Page<Order> getOrderByStatusAndRestaurant(Pageable pageable, int idStatus, int idRestaurant) {
        return orderPersistencePort.getOrderByStatusAndRestaurant(pageable, idStatus, idRestaurant);
    }

    @Override
    public void orderInProcess(int idEmployee, int idOrder) {
        orderPersistencePort.orderInProcess(idEmployee, idOrder);
    }

    @Override
    public Order orderReady(int idEmployee, int idOrder) {
        return orderPersistencePort.orderReady(idEmployee, idOrder);
    }

    @Override
    public void saveSecurityCode(int securityCode, int idOrder) {
        orderPersistencePort.saveSecurityCode(securityCode, idOrder);
    }

    @Override
    public void orderDelivered(int securityCode, int idOrder) {
        orderPersistencePort.orderDelivered(securityCode, idOrder);
    }
}
