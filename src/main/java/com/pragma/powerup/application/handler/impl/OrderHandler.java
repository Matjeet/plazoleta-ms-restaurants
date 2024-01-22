package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.client.ISmsFeignClient;
import com.pragma.powerup.application.client.IUsersFeignClient;
import com.pragma.powerup.application.dto.request.RegisterOrderRequestDto;
import com.pragma.powerup.application.dto.request.SmsInfoRequestDto;
import com.pragma.powerup.application.dto.response.DishPageResponseDto;
import com.pragma.powerup.application.dto.response.OrderDishListResponseDto;
import com.pragma.powerup.application.dto.response.OrderPageResponseDto;
import com.pragma.powerup.application.dto.response.UserInfoResponseDto;
import com.pragma.powerup.application.exceptions.DifferentRestaurantEmployeeException;
import com.pragma.powerup.application.handler.IOrderHandler;
import com.pragma.powerup.application.mapper.request.IOrderDishRequestMapper;
import com.pragma.powerup.application.mapper.request.IOrderRequestMapper;
import com.pragma.powerup.application.mapper.response.*;
import com.pragma.powerup.domain.Constants;
import com.pragma.powerup.domain.api.*;
import com.pragma.powerup.domain.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    private final IDishServicePort dishServicePort;
    private final IDishResponseMapper dishResponseMapper;
    private final ICategoryServicePort categoryServicePort;
    private final IOrderDishResponseMapper orderDishResponseMapper;
    private final IRestaurantServicePort restaurantServicePort;
    private final IOrderResponseMapper orderResponseMapper;
    private final IRestaurantResponseMapper restaurantResponseMapper;
    private final IStatusResponseMapper statusResponseMapper;
    private final IUsersFeignClient usersFeignClient;
    private final IHttpRequestContextHolderServicePort httpRequestContextHolderServicePort;
    private final ISmsFeignClient smsFeignClient;

    @Override
    public void saveOrder(RegisterOrderRequestDto registerOrderRequestDto) {

        int idStatus = statusServicePort.getStatusId(Constants.BACKORDER);

        int idOrder = orderServicePort.saveOrder(orderRequestMapper.toOrder(
                registerOrderRequestDto,
                idStatus
                )
        );

        orderDishServicePort.saveOrderDish(
                registerOrderRequestDto.getOrderDishRequestDtos().stream().map(
                        registerOrderDishRequestDto -> orderDishRequestMapper.toOrderDish(
                                registerOrderDishRequestDto,
                                idOrder
                        )
                ).collect(Collectors.toList())
        );
    }

    @Override
    public Page<OrderPageResponseDto> getOrderByStatusAndRestaurant(
            Pageable pageable,
            int idStatus,
            int idEmployee,
            int idRestaurant,
            int idOrder
    ) {

        if (usersFeignClient.validateRestaurantEmployee(
                httpRequestContextHolderServicePort.getToken(),
                idEmployee,
                idRestaurant
        ))
        {
            
            if (idOrder != 0) {
                orderServicePort.orderInProcess(idEmployee, idOrder);
            }

            Page<Order> orders = orderServicePort.getOrderByStatusAndRestaurant(
                    pageable,
                    idStatus,
                    idRestaurant
            );


            return orders.map(order -> {
                List<OrderDish> orderDishes = orderDishServicePort.getOrderDishList(order.getId());

                List<OrderDishListResponseDto> orderDishListResponseDtos = orderDishes.stream().map(
                        orderDish -> {

                            Dish dish = dishServicePort.getDish(orderDish.getIdDish());
                            Category category = categoryServicePort.getCategory(dish.getIdCategory());
                            Status status = statusServicePort.getStatus(dish.getIdStatus());

                            DishPageResponseDto dishPageResponseDto = dishResponseMapper.toDishPageDto(
                                    dish,
                                    category,
                                    status
                            );

                            return orderDishResponseMapper.toOrderDishDto(orderDish, dishPageResponseDto);
                        }).collect(Collectors.toList());

                Restaurant restaurant = restaurantServicePort.getRestaurant(order.getIdRestaurant());
                Status status = statusServicePort.getStatus(order.getIdStatus());
                return orderResponseMapper.toOrderPageDto(
                        order,
                        restaurantResponseMapper.toRestaurantsPageDto(restaurant),
                        statusResponseMapper.toStatusOrderDto(status),
                        orderDishListResponseDtos
                );
            });
        }
        else {
            throw new DifferentRestaurantEmployeeException();
        }

    }

    @Override
    public int orderReady(int idEmployee, int idOrder) {

        Order order = orderServicePort.orderReady(idEmployee, idOrder);
        Restaurant restaurant = restaurantServicePort.getRestaurant(order.getIdRestaurant());
        UserInfoResponseDto userInfoResponseDto = usersFeignClient.getClient(
                httpRequestContextHolderServicePort.getToken(),
                order.getIdClient()
        );

        SmsInfoRequestDto smsInfoRequestDto = orderRequestMapper.toSmsInfoDto(userInfoResponseDto, restaurant);

        int securityCode = smsFeignClient.sendSms(
                httpRequestContextHolderServicePort.getToken(),
                smsInfoRequestDto
        );

        orderServicePort.saveSecurityCode(securityCode,idOrder);

        return securityCode;
    }

    @Override
    public void orderDelivered(int securityCode, int irOrder) {

        int idStatus = statusServicePort.getStatusId(Constants.DELIVERED);

        orderServicePort.orderDelivered(securityCode, irOrder, idStatus);
    }
}
