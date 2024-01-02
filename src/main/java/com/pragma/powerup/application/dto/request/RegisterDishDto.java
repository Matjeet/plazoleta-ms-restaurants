package com.pragma.powerup.application.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDishDto {

    private String name;
    private int price;
    private String description;
    private String urlImage;
    private RegisterCategoryDto Category;
    private int idRestaurant;
}
