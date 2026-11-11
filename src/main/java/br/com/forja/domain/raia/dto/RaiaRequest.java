package br.com.forja.domain.raia.dto;

import jakarta.validation.constraints.Positive;

public record RaiaRequest(

                @Positive(message = "Número da raia deve ser maior que zero") Integer numero,

                String descricao

) {
}