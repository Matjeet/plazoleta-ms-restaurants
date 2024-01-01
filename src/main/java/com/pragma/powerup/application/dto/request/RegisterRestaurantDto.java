package com.pragma.powerup.application.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRestaurantDto {

    private int id;
    private String name;
    private String nit;
    private String address;
    private String phone;
    private String logoUrl;
    private int idOwner;
}
