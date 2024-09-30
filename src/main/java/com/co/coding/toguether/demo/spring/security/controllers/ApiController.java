package com.co.coding.toguether.demo.spring.security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/public")
    public ResponseEntity<String>  getPublicEndpoint(){
        passwordEncoder.encode("admin");
        return ResponseEntity.ok("Hello this is public endpoint");
    }
    @GetMapping("/private")
    public ResponseEntity<String> getPrivateEndpoint(){
        return ResponseEntity.ok("Hello this is private endpoint");
    }

    @GetMapping("/manager")
    public ResponseEntity<String> getManagerEndpoint(){
        return ResponseEntity.ok("Hello this is manager endpoint");
    }

    @GetMapping("/admin")
    public ResponseEntity<String> getDashboard() {
        return ResponseEntity.ok("Hello this is Admin Dashboard JWT Endpoint");
    }
}
