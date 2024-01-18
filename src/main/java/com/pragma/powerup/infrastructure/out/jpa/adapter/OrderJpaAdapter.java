package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.Constants;
import com.pragma.powerup.domain.model.Order;
import com.pragma.powerup.domain.spi.IOrderPersistencePort;
import com.pragma.powerup.infrastructure.exception.CustomerHasAnOrderException;
import com.pragma.powerup.infrastructure.exception.NotBackOrderStatusException;
import com.pragma.powerup.infrastructure.out.jpa.entity.OrderEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.StatusEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IOrderEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IOrderRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IRestaurantRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class OrderJpaAdapter implements IOrderPersistencePort {

    private final IOrderEntityMapper orderEntityMapper;
    private final IOrderRepository orderRepository;
    private final IRestaurantRepository restaurantRepository;
    private final IStatusRepository statusRepository;

    @Override
    public int saveOrder(Order order) {
        if (orderRepository.findByIdClient(order.getIdClient()).isPresent()){
            throw new CustomerHasAnOrderException();
        }
        else{
            StatusEntity statusEntity = statusRepository.getReferenceById(order.getIdStatus());
            if (statusEntity.getName().equals(Constants.BACKORDER) && order.getIdEmployee() == null){
                OrderEntity orderEntity = orderRepository.save(
                        orderEntityMapper.toEntity(
                                order,
                                restaurantRepository.getReferenceById(order.getIdRestaurant()),
                                statusEntity
                        )
                );

                return orderEntity.getId();
            }
            else {
                throw new IllegalArgumentException();
            }

        }
    }

    @Override
    public Page<Order> getOrderByStatusAndRestaurant(Pageable pageable, int idStatus, int idRestaurant) {
        Page<OrderEntity> orderEntities = orderRepository.findByStatusAndRestaurant(
                pageable,
                statusRepository.getReferenceById(idStatus),
                restaurantRepository.getReferenceById(idRestaurant)
        );
        return orderEntities.map(orderEntityMapper::toOrder);
    }

    @Override
    public void orderInProcess(int idEmployee, int idOrder) {

        OrderEntity orderEntity = orderRepository.getReferenceById(idOrder);

        if (orderEntity.getStatus().getName().equals(Constants.BACKORDER)){

            StatusEntity statusEntity = statusRepository.findByName(Constants.IN_PROCESS);
            orderEntity.setStatus(statusEntity);
            orderEntity.setIdEmployee(idEmployee);
            orderRepository.save(orderEntity);

        }
        else {
            throw new NotBackOrderStatusException();
        }
    }
}
