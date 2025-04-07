package com.example.library.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.Base64;
import java.security.Key;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtil {
    @Value("${spring.security.jwt.secret}")
    private String secretKey;

    private static final long EXPIRATION_TIME = 86400000L; // 1 день (в миллисекундах)

    /**
     * Получает ключ для подписи JWT (SecretKey)
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Генерирует JWT-токен для пользователя
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username) // Указываем пользователя
                .issuedAt(new Date()) // Дата создания токена
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Дата истечения
                .signWith(getSigningKey()) // Подписываем токен
                .compact(); // Генерируем строку токена
    }

    /**
     * Извлекает информацию (Claims) из токена
     */
    public Claims extractClaims(String token) {
        return Jwts.parser()  // Используем Jwts.parser()
                .verifyWith(getSigningKey())  // Передаем SecretKey
                .build()
                .parseSignedClaims(token)  // Парсим токен
                .getPayload();  // Получаем Claims
    }

    /**
     * Извлекает имя пользователя из токена
     */
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    /**
     * Проверяет, не истек ли токен
     */
    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    /**
     * Проверяет валидность токена
     */
    public boolean validateToken(String token, String username) {
        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
    }

}
