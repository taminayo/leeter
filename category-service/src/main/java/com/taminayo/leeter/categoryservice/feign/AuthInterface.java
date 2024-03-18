package com.taminayo.leeter.categoryservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("AUTH-SERVICE")
public interface AuthInterface {

    @GetMapping("auth/validate")
    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String token);
}