package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.RegisterOrderRequestDto;
import com.pragma.powerup.application.handler.IOrderHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Place an order for one or several dishes from a specific restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "The order was created successfully",
                    content = @Content),
            @ApiResponse(responseCode = "401",
                    description = "Only the client can place an order",
                    content = @Content)
    })
    @PreAuthorize("hasRole('ROLE_cliente')")
    @PostMapping("/")
    public ResponseEntity<Void> saveOrder(@RequestBody RegisterOrderRequestDto registerOrderRequestDto){
        orderHandler.saveOrder(registerOrderRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
}
