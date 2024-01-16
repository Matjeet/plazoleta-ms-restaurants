package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.RegisterOrderRequestDto;

public interface IOrderHandler {

    void saveOrder(RegisterOrderRequestDto registerOrderRequestDto);
}
