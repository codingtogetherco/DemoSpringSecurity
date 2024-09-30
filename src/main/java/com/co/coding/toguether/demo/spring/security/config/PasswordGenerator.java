package com.co.coding.toguether.demo.spring.security.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "manager";
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println(encodedPassword); // Esto te dará el hash encriptado
    }
}