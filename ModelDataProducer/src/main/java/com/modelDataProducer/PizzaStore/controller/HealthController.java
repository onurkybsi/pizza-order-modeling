package com.modelDataProducer.PizzaStore.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class HealthController {

    @GetMapping("/checkHealth")
    @ResponseStatus(value = HttpStatus.OK)
    public void checkHealth() {
        // I'm healthy !
    }
}
