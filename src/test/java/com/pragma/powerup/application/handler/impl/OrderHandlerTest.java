package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.RegisterOrderDishRequestDto;
import com.pragma.powerup.application.dto.request.RegisterOrderRequestDto;
import com.pragma.powerup.application.mapper.request.IOrderDishRequestMapper;
import com.pragma.powerup.application.mapper.request.IOrderRequestMapper;
import com.pragma.powerup.domain.api.IOrderDishServicePort;
import com.pragma.powerup.domain.api.IOrderServicePort;
import com.pragma.powerup.domain.api.IStatusServicePort;
import com.pragma.powerup.domain.model.Order;
import com.pragma.powerup.domain.model.OrderDish;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderHandlerTest {

    private RegisterOrderRequestDto registerOrderRequestDto;
    private RegisterOrderDishRequestDto registerOrderDishRequestDto;
    private Order order;
    private OrderDish orderDish;

    @Mock
    private IOrderRequestMapper orderRequestMapper;
    @Mock
    private IOrderDishRequestMapper orderDishRequestMapper;
    @Mock
    private IOrderServicePort orderServicePort;
    @Mock
    private IOrderDishServicePort orderDishServicePort;
    @Mock
    private IStatusServicePort statusServicePort;

    @InjectMocks
    private OrderHandler orderHandler;

    @BeforeEach
    void setUp() {
        registerOrderRequestDto = new RegisterOrderRequestDto();
        registerOrderDishRequestDto = new RegisterOrderDishRequestDto();
        order = new Order();
        orderDish = new OrderDish();
    }

    @Test
    void saveOrder() {

        registerOrderDishRequestDto.setQuantity(1);
        registerOrderDishRequestDto.setIdDish(1);

        registerOrderRequestDto.setIdRestaurant(1);
        registerOrderRequestDto.setIdClient(1);
        registerOrderRequestDto.setOrderDishRequestDtos(List.of(registerOrderDishRequestDto, registerOrderDishRequestDto));

        order.setId(1);
        order.setIdRestaurant(1);
        order.setIdClient(1);
        order.setIdStatus(1);

        orderDish.setId(1);
        orderDish.setQuantity(1);
        orderDish.setIdDish(1);
        orderDish.setIdOrder(1);

        when(statusServicePort.getStatusId("pendiente")).thenReturn(1);
        when(orderRequestMapper.toOrder(registerOrderRequestDto, 1)).thenReturn(order);
        when(orderServicePort.saveOrder(order)).thenReturn(1);
        when(orderDishRequestMapper.toOrderDish(registerOrderDishRequestDto, 1)).thenReturn(orderDish);
        doNothing().when(orderDishServicePort).saveOrderDish(List.of(orderDish, orderDish));

        orderHandler.saveOrder(registerOrderRequestDto);

        verify(statusServicePort, times(1)).getStatusId("pendiente");
        verify(orderRequestMapper,times(1)).toOrder(registerOrderRequestDto, 1);
        verify(orderServicePort, times(1)).saveOrder(order);
        verify(orderDishRequestMapper, times(2)).toOrderDish(registerOrderDishRequestDto,1);
        verify(orderDishServicePort, times(1)).saveOrderDish(List.of(orderDish, orderDish));
    }
}