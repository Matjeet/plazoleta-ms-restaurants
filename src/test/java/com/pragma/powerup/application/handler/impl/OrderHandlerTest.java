package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.client.ISmsFeignClient;
import com.pragma.powerup.application.client.IUsersFeignClient;
import com.pragma.powerup.application.dto.request.RegisterOrderDishRequestDto;
import com.pragma.powerup.application.dto.request.RegisterOrderRequestDto;
import com.pragma.powerup.application.dto.request.SmsInfoRequestDto;
import com.pragma.powerup.application.dto.response.UserInfoResponseDto;
import com.pragma.powerup.application.mapper.request.IOrderDishRequestMapper;
import com.pragma.powerup.application.mapper.request.IOrderRequestMapper;
import com.pragma.powerup.domain.api.*;
import com.pragma.powerup.domain.model.Order;
import com.pragma.powerup.domain.model.OrderDish;
import com.pragma.powerup.domain.model.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
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
    private Restaurant restaurant;
    private UserInfoResponseDto userInfoResponseDto;
    private SmsInfoRequestDto smsInfoRequestDto;

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
    @Mock
    private IRestaurantServicePort restaurantServicePort;
    @Mock
    private IUsersFeignClient usersFeignClient;
    @Mock
    private IHttpRequestContextHolderServicePort httpRequestContextHolderServicePort;
    @Mock
    private ISmsFeignClient smsFeignClient;

    @InjectMocks
    private OrderHandler orderHandler;

    @BeforeEach
    void setUp() {
        registerOrderRequestDto = new RegisterOrderRequestDto();
        registerOrderDishRequestDto = new RegisterOrderDishRequestDto();
        order = new Order();
        orderDish = new OrderDish();
        restaurant = new Restaurant();
        userInfoResponseDto = new UserInfoResponseDto();
        smsInfoRequestDto = new SmsInfoRequestDto();

        order.setId(1);
        order.setIdRestaurant(1);
        order.setIdClient(1);
        order.setIdStatus(1);
    }

    @Test
    void saveOrder() {

        registerOrderDishRequestDto.setQuantity(1);
        registerOrderDishRequestDto.setIdDish(1);

        registerOrderRequestDto.setIdRestaurant(1);
        registerOrderRequestDto.setIdClient(1);
        registerOrderRequestDto.setOrderDishRequestDtos(List.of(registerOrderDishRequestDto, registerOrderDishRequestDto));

        orderDish.setId(1);
        orderDish.setQuantity(1);
        orderDish.setIdDish(1);
        orderDish.setIdOrder(1);

        when(statusServicePort.getStatusId(anyString())).thenReturn(1);
        when(orderRequestMapper.toOrder(registerOrderRequestDto, 1)).thenReturn(order);
        when(orderServicePort.saveOrder(order)).thenReturn(1);
        when(orderDishRequestMapper.toOrderDish(registerOrderDishRequestDto, 1)).thenReturn(orderDish);
        doNothing().when(orderDishServicePort).saveOrderDish(List.of(orderDish, orderDish));

        orderHandler.saveOrder(registerOrderRequestDto);

        verify(statusServicePort, times(1)).getStatusId(anyString());
        verify(orderRequestMapper,times(1)).toOrder(registerOrderRequestDto, 1);
        verify(orderServicePort, times(1)).saveOrder(order);
        verify(orderDishRequestMapper, times(2)).toOrderDish(registerOrderDishRequestDto,1);
        verify(orderDishServicePort, times(1)).saveOrderDish(List.of(orderDish, orderDish));
    }

    @Test
    void orderReady(){

        restaurant.setId(1);
        restaurant.setName("");
        restaurant.setNit("");
        restaurant.setAddress("");
        restaurant.setPhone("");
        restaurant.setLogoUrl("");
        restaurant.setIdOwner(1);

        userInfoResponseDto.setId(1);
        userInfoResponseDto.setName("");
        userInfoResponseDto.setLastName("");
        userInfoResponseDto.setDocumentId("");
        userInfoResponseDto.setPhoneNumber("");
        userInfoResponseDto.setBirthDate(LocalDate.of(1,1,1));
        userInfoResponseDto.setEmail("");
        userInfoResponseDto.setRoleId(1);

        smsInfoRequestDto.setPhoneNumber("");
        smsInfoRequestDto.setRestaurantName("");
        smsInfoRequestDto.setName("");

        when(orderServicePort.orderReady(anyInt(), anyInt())).thenReturn(order);
        when(restaurantServicePort.getRestaurant(anyInt())).thenReturn(restaurant);
        when(httpRequestContextHolderServicePort.getToken()).thenReturn("");
        when(usersFeignClient.getClient(anyString(),anyInt())).thenReturn(userInfoResponseDto);
        when(orderRequestMapper.toSmsInfoDto(userInfoResponseDto,restaurant)).thenReturn(smsInfoRequestDto);
        when(smsFeignClient.sendSms("", smsInfoRequestDto)).thenReturn(1);
        doNothing().when(orderServicePort).saveSecurityCode(anyInt(),anyInt());

        int result = orderHandler.orderReady(1,1);

        verify(orderServicePort, times(1)).orderReady(anyInt(),anyInt());
        verify(restaurantServicePort, times(1)).getRestaurant(anyInt());
        verify(httpRequestContextHolderServicePort, times(2)).getToken();
        verify(usersFeignClient, times(1)).getClient(anyString(), anyInt());
        verify(orderRequestMapper, times(1)).toSmsInfoDto(userInfoResponseDto, restaurant);
        verify(smsFeignClient, times(1)).sendSms("", smsInfoRequestDto);
        verify(orderServicePort).saveSecurityCode(anyInt(), anyInt());
        assertInstanceOf(Integer.class, result);

    }
}