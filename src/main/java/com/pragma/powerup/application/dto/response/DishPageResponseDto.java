package com.pragma.powerup.application.dto.response;

import com.pragma.powerup.application.dto.CategoryDto;
import com.pragma.powerup.application.dto.StatusDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishPageResponseDto {

    private String name;
    private int price;
    private String description;
    private String urlImage;
    private CategoryDto category;
    private StatusDto status;
}
