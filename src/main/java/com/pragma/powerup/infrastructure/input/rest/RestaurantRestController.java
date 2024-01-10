package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.RegisterRestaurantDto;
import com.pragma.powerup.application.dto.response.RestaurantsPageResponseDto;
import com.pragma.powerup.application.handler.IRestaurantHandler;
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

@RestController
@RequestMapping("restaurant/v1/")
@RequiredArgsConstructor
public class RestaurantRestController {

    private final IRestaurantHandler restaurantHandler;

    @Operation(summary = "create a new restaurant")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Restaurant created successful",
                    content = @Content),
            @ApiResponse(
                    responseCode = "401",
                    description = "The user is not an owner",
                    content = @Content),
            @ApiResponse(
                    responseCode = "403",
                    description = "Only users with 'administrador' role could create a restaurant",
                    content = @Content)
    })
    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_administrador')")
    public ResponseEntity<Void> saveRestaurant(@RequestBody RegisterRestaurantDto registerRestaurantDto){
        if(restaurantHandler.saveRestaurant(registerRestaurantDto)){
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .build();
        }
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .build();
    }

    @GetMapping("/restaurants/{page}/{size}")
    @PreAuthorize("hasRole('ROLE_cliente')")
    public Page<RestaurantsPageResponseDto> getRestaurants(@PathVariable int page, @PathVariable int size){
        Pageable pageable = PageRequest.of(page, size);
        return restaurantHandler.getRestaurants(pageable);
    }
}
