package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.RegisterOrderRequestDto;
import com.pragma.powerup.application.dto.response.OrderPageResponseDto;
import com.pragma.powerup.application.handler.IOrderHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Operation(
            summary = "Get a page of orders from a specific restaurant, " +
                    "filtered by status and assign an order to an employee"
    )
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
    @GetMapping("/assign/{page}/{size}/{idStatus}/{idEmployee}/{idRestaurant}/{idOrder}")
    public ResponseEntity<List<OrderPageResponseDto>> assignOrder(
            @PathVariable int page,
            @PathVariable int size,
            @PathVariable int idStatus,
            @PathVariable int idEmployee,
            @PathVariable int idRestaurant,
            @PathVariable int idOrder
    ) {
        Pageable pageable = PageRequest.of(page, size);

        Page<OrderPageResponseDto> pageResponseDtos = orderHandler.getOrderByStatusAndRestaurant(
                pageable,
                idStatus,
                idEmployee,
                idRestaurant,
                idOrder
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pageResponseDtos.getContent());
    }


    @Operation(summary = "Change the order's status from 'en_preparación' to 'listo'," +
            " send a SMS to the client and generate a securityCode")
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
    @GetMapping("/ready/{idEmployee}/{idOrder}")
    public ResponseEntity<Integer> orderReady(@PathVariable int idEmployee, @PathVariable int idOrder){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderHandler.orderReady(idEmployee, idOrder));
    }

    @Operation(summary = "Change the order's status from 'listo' to 'entregado' using the security code")
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
    @GetMapping("/deliver/{securityCode}/{idOrder}")
    public ResponseEntity<Void> orderDelivered(@PathVariable int securityCode, @PathVariable int idOrder){

        orderHandler.orderDelivered(securityCode, idOrder);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @Operation(summary = "Change the order's status from 'entregado' to 'listo'")
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
    @GetMapping("/undo-delivered/{idEmployee}/{idOrder}")
    public ResponseEntity<Void> undoDelivered(@PathVariable int idEmployee, @PathVariable int idOrder){

        orderHandler.undoDelivered(idEmployee, idOrder);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
