package com.pragma.powerup.application.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmsInfoRequestDto {

    private String phoneNumber;
    private String name;
    private String restaurantName;
}
