package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.Order;
import com.pragma.powerup.domain.spi.IOrderPersistencePort;
import com.pragma.powerup.infrastructure.out.jpa.entity.OrderEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IOrderEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IOrderRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IRestaurantRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IStatusRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderJpaAdapter implements IOrderPersistencePort {

    private final IOrderEntityMapper orderEntityMapper;
    private final IOrderRepository orderRepository;
    private final IRestaurantRepository restaurantRepository;
    private final IStatusRepository statusRepository;

    @Override
    public int saveOrder(Order order) {
        OrderEntity orderEntity = orderRepository.save(
                orderEntityMapper.toEntity(
                        order,
                        restaurantRepository.getReferenceById(order.getIdRestaurant()),
                        statusRepository.getReferenceById(order.getIdStatus())
                )
        );
        return orderEntity.getId();
    }
}
