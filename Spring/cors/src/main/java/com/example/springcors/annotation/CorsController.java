package com.example.springcors.annotation;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CorsController {

    @GetMapping("/cors/v1")
    public String corsV1() {
        return "ok";
    }

    @CrossOrigin(origins = "http//localhost:8080", allowedHeaders = "*")
    @GetMapping("/cors/v2")
    public String corsV2() {
        return "ok";
    }
}
