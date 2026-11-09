package br.com.forja.security.dto;

public record LoginRequest(
        String email,
        String senha
) {
}