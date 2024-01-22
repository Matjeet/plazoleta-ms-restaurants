package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.Constants;
import com.pragma.powerup.domain.model.Order;
import com.pragma.powerup.domain.spi.IOrderPersistencePort;
import com.pragma.powerup.infrastructure.exception.*;
import com.pragma.powerup.infrastructure.out.jpa.entity.OrderCodeEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.OrderEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.StatusEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IOrderCodeEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IOrderEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IOrderCodeRepository;
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
    private final IOrderCodeRepository orderCodeRepository;
    private final IOrderCodeEntityMapper orderCodeEntityMapper;

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

    @Override
    public Order orderReady(int idEmployee, int idOrder) {

        OrderEntity orderEntity = orderRepository.getReferenceById(idOrder);

        if(orderEntity.getStatus().getName().equals(Constants.IN_PROCESS) &&
            orderEntity.getIdEmployee() == idEmployee){

            StatusEntity statusEntity = statusRepository.findByName(Constants.READY);
            orderEntity.setStatus(statusEntity);
            return orderEntityMapper.toOrder(orderRepository.save(orderEntity));
        }
        else {
            throw new IncorrectStatusOrIdEmployeeException();
        }
    }

    @Override
    public void saveSecurityCode(int securityCode, int idOrder) {
        OrderEntity orderEntity = orderRepository.getReferenceById(idOrder);
        orderCodeRepository.save(orderCodeEntityMapper.toOrderCodeEntity(securityCode, orderEntity));
    }

    @Override
    public void orderDelivered(int securityCode, int idOrder, int idStatus) {

        if(orderCodeRepository.findBySecurityCode(securityCode).isPresent()){

            OrderCodeEntity orderCodeEntity = orderCodeRepository.findBySecurityCode(securityCode).get();

            if(orderCodeEntity.getOrder().getId() == idOrder &&
                orderCodeEntity.getOrder().getStatus().getName().equals(Constants.READY)){

                StatusEntity status = statusRepository.getReferenceById(idStatus);
                orderCodeEntity.getOrder().setStatus(status);
                orderCodeRepository.save(orderCodeEntity);
            }else {
                throw new DifferentSecurityCodeOrderException();
            }

        }else{
            throw new NoDataFoundException();
        }
    }

    @Override
    public void undoDelivered(int idOrder, int idStatus) {

        OrderEntity orderEntity = orderRepository.getReferenceById(idOrder);
        if(orderEntity.getStatus().getName().equals(Constants.DELIVERED)){

            StatusEntity status = statusRepository.getReferenceById(idStatus);
            orderEntity.setStatus(status);
            orderRepository.save(orderEntity);
        }
        else{
            throw new OrderIsNotDeliveredException();
        }

    }
}
