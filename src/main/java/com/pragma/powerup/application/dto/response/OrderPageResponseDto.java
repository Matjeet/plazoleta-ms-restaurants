package com.pragma.powerup.application.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderPageResponseDto {

    private int id;
    private int idClient;
    private Integer idEmployee;
    private RestaurantsPageResponseDto restaurant;
    private StatusOrderResponseDto status;
    private List<OrderDishListResponseDto> orderDishes;
}
