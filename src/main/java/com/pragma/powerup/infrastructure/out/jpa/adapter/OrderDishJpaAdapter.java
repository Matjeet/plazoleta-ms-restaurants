package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.OrderDish;
import com.pragma.powerup.domain.spi.IOrderDishPersistencePort;
import com.pragma.powerup.infrastructure.out.jpa.entity.OrderDishEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IOrderDishEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IDishRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IOrderDishRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IOrderRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class OrderDishJpaAdapter implements IOrderDishPersistencePort {

    private final IOrderDishEntityMapper orderDishEntityMapper;
    private final IOrderDishRepository orderDishRepository;
    private final IDishRepository dishRepository;
    private final IOrderRepository orderRepository;
    @Override
    public void saveOrderDish(List<OrderDish> orderDishes) {

        Stream<OrderDishEntity> orderDishEntities = orderDishes.stream().map(
                orderDish -> orderDishEntityMapper.toEntity(
                        orderDish,
                        dishRepository.getReferenceById(orderDish.getIdDish()),
                        orderRepository.getReferenceById(orderDish.getIdOrder())
                )
        );
        
        orderDishEntities.forEach(orderDishRepository::save);

    }

    @Override
    public List<OrderDish> getOrderDishList(int idOrder) {
        return orderRepository.findAllByIdOrder(idOrder);
    }
}
