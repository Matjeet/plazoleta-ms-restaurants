package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.RegisterOrderRequestDto;
import com.pragma.powerup.application.dto.response.OrderPageResponseDto;
import com.pragma.powerup.application.handler.IOrderHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Operation(summary = "Get a page of orders from a specific restaurant and filtered by status")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "The request has been answered successfully",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Only an employee can make a request"
            )
    })
    @PreAuthorize("hasRole('ROLE_empleado')")
    @GetMapping("/orders/{page}/{size}/{idStatus}/{idEmployee}/{idRestaurant}")
    public ResponseEntity<List<OrderPageResponseDto>> getOrders(
            @PathVariable int page,
            @PathVariable int size,
            @PathVariable int idStatus,
            @PathVariable int idEmployee,
            @PathVariable int idRestaurant
    ) {
        Pageable pageable = PageRequest.of(page, size);

        Page<OrderPageResponseDto> pageResponseDtos = orderHandler.getOrderByStatusAndRestaurant(
                pageable,
                idStatus,
                idEmployee,
                idRestaurant,
                0
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pageResponseDtos.getContent());
    }
}
