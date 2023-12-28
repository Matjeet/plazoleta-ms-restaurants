package com.pragma.powerup.application.client;

import com.pragma.powerup.application.client.configuration.FeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(
        name = "microservice-users",
        url = "http://localhost:8081"
)
@RequestMapping("/owner")
public interface IOwnerFeignClient {

    @GetMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    boolean validateOwnerRole(@PathVariable("id") int id);
}
