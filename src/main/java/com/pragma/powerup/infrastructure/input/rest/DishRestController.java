package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.RegisterDishDto;
import com.pragma.powerup.application.dto.request.UpdateDishDto;
import com.pragma.powerup.application.handler.IDishHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("dish/v1/")
@RequiredArgsConstructor
public class DishRestController {

    private final IDishHandler dishHandler;

    @Operation(summary = "Create a restaurant menu dish")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "The dish was created successful",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Only the restaurant owner can create a dish",
                    content = @Content)
    })
    @PreAuthorize("hasRole('ROLE_propietario')")
    @PostMapping("/create")
    public ResponseEntity<Void> createDish(@RequestBody RegisterDishDto registerDishDto){
        dishHandler.saveDish(registerDishDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Update a restaurant menu dish")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "The dish was updated successful",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Only the restaurant owner can update a dish",
                    content = @Content)
    })
    @PreAuthorize("hasRole('ROLE_propietario')")
    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateDish(@PathVariable int id, @RequestBody UpdateDishDto updateDishDto){
        dishHandler.updateDish(updateDishDto, id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Change the dish's status")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "The status changed successful",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Only the owner can change the status",
                    content = @Content
            )
    })
    @PreAuthorize("hasRole('ROLE_propietario')")
    @GetMapping("/changeStatus/{idDish}/{idOwner}")
    public ResponseEntity<String> changeStatus(@PathVariable int idDish, @PathVariable int idOwner) {
        String status = dishHandler.changeStatus(idDish, idOwner);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("El plato ahora está " + status);
    }
}
