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
        String token = authService.register(request);
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping(value = "/login", consumes = "application/json")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");
        String token = authService.login(email, password);
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/recuperar")
    public ResponseEntity<?> solicitarRecuperacion(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            authService.enviarEnlaceRecuperacion(email);
            return ResponseEntity.ok(Map.of(
                    "mensaje", "Enlace enviado"
            ));
        } catch (RuntimeException e) {
            // Esta excepción es la del "usuario no encontrado"
            return ResponseEntity.badRequest().body(Map.of(
                    "error", e.getMessage()
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of(
                    "error", "Error interno al enviar el correo"
            ));
        }
    }



    @PostMapping("/restablecer")
    public ResponseEntity<?> restablecerPassword(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        String nuevaPassword = request.get("password");
        authService.restablecerPassword(token, nuevaPassword);
        return ResponseEntity.ok(Map.of(
                "mensaje", "Contraseña actualizada correctamente."
        ));
    }
}





