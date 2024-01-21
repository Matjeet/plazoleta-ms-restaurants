package com.pragma.powerup.application.client;


import com.pragma.powerup.application.dto.response.UserInfoResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "microservice-users",
        url = "http://localhost:8081"
)
public interface IUsersFeignClient {

    @GetMapping(value = "/owner/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    boolean validateOwnerRole(@RequestHeader("Authorization") String token, @PathVariable int id);

    @GetMapping(
            value = "/employee/v1/validate-restaurant/{idEmployee}/{idRestaurant}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    boolean validateRestaurantEmployee(
            @RequestHeader("Authorization") String token,
            @PathVariable int idEmployee,
            @PathVariable int idRestaurant
    );

    @GetMapping(value = "/client/v1/info/{idClient}}", consumes = MediaType.APPLICATION_JSON_VALUE)
    UserInfoResponseDto getClient(@RequestHeader("Authorization") String token,@PathVariable int idClient);
}
