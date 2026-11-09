package br.com.forja.domain.venda.dto;

import jakarta.validation.constraints.NotNull;

public record ItemVendaRequest(

        @NotNull(message = "Produto é obrigatório")
        Long produtoId,

        @NotNull(message = "Quantidade é obrigatória")
        Integer quantidade

) {
}