package br.com.forja.domain.estoque.dto;

import jakarta.validation.constraints.NotNull;

public record EntradaEstoqueRequest(

                @NotNull(message = "Produto é obrigatório") Long produtoId,

                @NotNull(message = "Quantidade é obrigatória") Integer quantidade,

                String observacao

) {
}