package org.example.orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.service.CityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cities")
public class CityController {

    private final CityService cityService;


    @GetMapping("/sync-cities")
    public ResponseEntity<Void> syncCities(){
        cityService.syncCities();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
