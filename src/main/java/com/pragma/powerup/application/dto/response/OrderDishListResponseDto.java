package com.pragma.powerup.application.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDishListResponseDto {

    private String quantity;
    private DishPageResponseDto dish;
}
