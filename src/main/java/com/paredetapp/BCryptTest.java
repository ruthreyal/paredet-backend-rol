package com.paredetapp;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptTest {
    public static void main(String[] args) {
        String rawPassword = "1234";
        String hashed = new BCryptPasswordEncoder().encode(rawPassword);
        System.out.println("Contraseña encriptada: " + hashed);
    }
}

