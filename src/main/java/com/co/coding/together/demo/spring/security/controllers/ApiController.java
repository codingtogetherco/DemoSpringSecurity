package com.co.coding.together.demo.spring.security.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping("/public")
    public String getPublicEndpoint(){
        return "Hello this is public endpoint";
    }

    @GetMapping("/private")
    public String getPrivateEndpoint(){
        return "Hello this is private endpoint";
    }

    @GetMapping("/manager")
    public String getManagerEndpoint(){
        return "Hello this is manager endpoint";
    }
}
