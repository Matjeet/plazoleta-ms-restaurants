package com.pragma.powerup.application.client;

import com.pragma.powerup.application.dto.request.SmsInfoRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "microservice-sms",
        url = "http://localhost:8083"
)
public interface ISmsFeignClient {

    @PostMapping(value = "/sms/v1/send", consumes = MediaType.APPLICATION_JSON_VALUE)
    int sendSms(@RequestHeader("Authorization") String token, @RequestBody SmsInfoRequestDto smsInfoRequestDto);
}
