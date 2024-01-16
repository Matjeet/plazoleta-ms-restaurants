package com.pragma.powerup.application.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RegisterOrderRequestDto {

    private int idRestaurant;
    private int idClient;
    private List<RegisterOrderDishRequestDto> orderDishRequestDtos;
}
