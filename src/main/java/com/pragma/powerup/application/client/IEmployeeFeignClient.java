package com.pragma.powerup.application.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;


@FeignClient(
        name = "microservice-users",
        url = "http://localhost:8081/employee/v1"
)
public interface IEmployeeFeignClient {

    @GetMapping(
            value = "/validate-restaurant/{idEmployee}/{idRestaurant}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    boolean validateRestaurantEmployee(
            @RequestHeader("Authorization") String token,
            @PathVariable int idEmployee,
            @PathVariable int idRestaurant
            );
}
