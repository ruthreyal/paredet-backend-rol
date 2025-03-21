package com.paredetapp.security;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class SecurityChecker {
    @PostConstruct
    public void init() {
        System.out.println("✅ SecurityConfig ha sido detectado por Spring!");
    }
}
