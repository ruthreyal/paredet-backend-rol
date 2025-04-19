package com.paredetapp.controller;

import com.paredetapp.model.Usuario;
import com.paredetapp.security.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Usuario request) {
        System.out.println("🔧 Registro solicitado para: " + request.getEmail());

        String token = authService.register(request);

        System.out.println("✅ Registro completado. Token generado.");
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping(value = "/login", consumes = "application/json")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        System.out.println("✅ ENTRANDO AL MÉTODO LOGIN");
        String email = request.get("email");
        String password = request.get("password");
        System.out.println("🔐 Email: " + email);
        System.out.println("🔑 Password: " + password);
        String token = authService.login(email, password);
        return ResponseEntity.ok(Map.of("token", token));
    }
}



