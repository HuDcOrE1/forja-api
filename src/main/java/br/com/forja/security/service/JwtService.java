package br.com.forja.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class JwtService {

        @Value("${jwt.secret}")
        private String secret;

        @Value("${jwt.expiration}")
        private Long expiration;

        private SecretKey secretKey;

        @PostConstruct
        public void init() {

                this.secretKey = Keys.hmacShaKeyFor(
                                secret.getBytes(
                                                StandardCharsets.UTF_8));
        }

        public String gerarToken(
                        String email,
                        Collection<String> roles) {

                return Jwts.builder()
                                .subject(email)
                                .claim("roles", roles)
                                .issuedAt(new Date())
                                .expiration(
                                                new Date(
                                                                System.currentTimeMillis()
                                                                                + expiration))
                                .signWith(secretKey)
                                .compact();
        }

        public String extrairEmail(String token) {

                return obterClaims(token)
                                .getSubject();
        }

        public List<String> extrairRoles(
                        String token) {

                Object roles = obterClaims(token)
                                .get("roles");

                if (roles instanceof List<?> lista) {

                        return lista
                                        .stream()
                                        .map(Object::toString)
                                        .toList();
                }

                return List.of();
        }

        public boolean tokenValido(String token) {

                try {

                        Jwts.parser()
                                        .verifyWith(secretKey)
                                        .build()
                                        .parseSignedClaims(token);

                        return true;

                } catch (Exception ex) {

                        return false;
                }
        }

        private Claims obterClaims(
                        String token) {

                return Jwts.parser()
                                .verifyWith(secretKey)
                                .build()
                                .parseSignedClaims(token)
                                .getPayload();
        }
}