package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.RegisterDishDto;
import com.pragma.powerup.application.handler.IDishHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dish/v1/")
@RequiredArgsConstructor
public class DishRestController {

    private final IDishHandler dishHandler;

    @PreAuthorize("hasRole('ROLE_propietario')")
    @PostMapping("/create")
    public ResponseEntity<Void> createDish(@RequestBody RegisterDishDto registerDishDto){
        dishHandler.saveDish(registerDishDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
