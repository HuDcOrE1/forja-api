package br.com.forja.domain.estoque.dto;

import jakarta.validation.constraints.NotNull;

public record SaidaEstoqueRequest(

        @NotNull(message = "Produto é obrigatório")
        Long produtoId,

        @NotNull(message = "Quantidade é obrigatória")
        Integer quantidade,

        String observacao

) {
}