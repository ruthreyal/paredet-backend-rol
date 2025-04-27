package com.paredetapp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Servicio para generar y validar tokens JWT para autenticación de usuarios.
 */
@Service
public class JwtService {

    private static final String SECRET_KEY = "clave_supersecreta";

    /**
     * Extrae el subject (normalmente el ID del usuario) del token JWT.
     */
    public String extractUserId(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrae el rol del usuario desde los claims del token JWT.
     */
    public String extractUserRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    /**
     * Extrae un claim específico usando una función pasada como parámetro.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Genera un token JWT para el usuario, incluyendo su rol como claim.
     */
    public String generateToken(String userId, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        return createToken(claims, userId);
    }

    /**
     * Verifica si el token es válido para el usuario dado.
     */
    public boolean isTokenValid(String token, String userId) {
        final String extractedId = extractUserId(token);
        return (extractedId.equals(userId)) && !isTokenExpired(token);
    }

    /**
     * Comprueba si el token ha expirado.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extrae la fecha de expiración del token.
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extrae todos los claims contenidos en el token.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Crea un token JWT a partir de claims personalizados y un subject.
     */
    private String createToken(Map<String, Object> claims, String subject) {
        long expirationMillis = 1000 * 60 * 60 * 10; // 10 horas
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}





