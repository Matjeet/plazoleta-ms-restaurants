package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.RegisterOrderRequestDto;
import com.pragma.powerup.application.handler.IOrderHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order/v1/")
@RequiredArgsConstructor
public class OrderRestController {

    private final IOrderHandler orderHandler;

    @PreAuthorize("hasRole('ROLE_cliente')")
    @PostMapping("/")
    public ResponseEntity<Void> saveOrder(@RequestBody RegisterOrderRequestDto registerOrderRequestDto){
        orderHandler.saveOrder(registerOrderRequestDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
