package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.RegisterOrderRequestDto;
import com.pragma.powerup.application.handler.IOrderHandler;
import com.pragma.powerup.application.mapper.request.IOrderDishRequestMapper;
import com.pragma.powerup.application.mapper.request.IOrderRequestMapper;
import com.pragma.powerup.domain.api.IOrderDishServicePort;
import com.pragma.powerup.domain.api.IOrderServicePort;
import com.pragma.powerup.domain.api.IStatusServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderHandler implements IOrderHandler {

    private final IOrderRequestMapper orderRequestMapper;
    private final IOrderDishRequestMapper orderDishRequestMapper;
    private final IOrderServicePort orderServicePort;
    private final IOrderDishServicePort orderDishServicePort;
    private final IStatusServicePort statusServicePort;

    private static final String BACKORDER = "pendiente";
    @Override
    public void saveOrder(RegisterOrderRequestDto registerOrderRequestDto) {

        int idStatus = statusServicePort.getStatusId(BACKORDER);

        int idOrder = orderServicePort.saveOrder(orderRequestMapper.toOrder(registerOrderRequestDto, idStatus));

        orderDishServicePort.saveOrderDish(
                registerOrderRequestDto.getOrderDishRequestDtos().stream().map(
                        registerOrderDishRequestDto -> orderDishRequestMapper.toOrderDish(
                                registerOrderDishRequestDto,
                                idOrder
                        )
                ).collect(Collectors.toList())
        );
    }
}
