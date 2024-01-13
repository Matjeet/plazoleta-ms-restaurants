package com.pragma.powerup.application.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterOrderDishRequestDto {

    private int quantity;
    private int idDish;
}
