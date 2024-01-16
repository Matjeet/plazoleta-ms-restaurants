package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.RegisterOrderRequestDto;
import com.pragma.powerup.application.dto.response.OrderPageResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IOrderHandler {

    void saveOrder(RegisterOrderRequestDto registerOrderRequestDto);

    Page<OrderPageResponseDto> getOrderByStatus(Pageable pageable, int idStatus);
}
