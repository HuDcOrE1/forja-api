package br.com.forja.domain.produto.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProdutoRequest(

        @NotBlank(message = "Código é obrigatório")
        String codigo,

        @NotBlank(message = "Nome é obrigatório")
        String nome,

        String descricao,

        @NotNull(message = "Categoria é obrigatória")
        Long categoriaId,

        @Positive(
        message = "Estoque deve ser maior que zero")
        Integer estoque,

        @NotNull(message = "Valor de custo é obrigatório")
        @DecimalMin(
                value = "0.01",
                message = "Valor de custo deve ser maior que zero"
        )
        BigDecimal valorCusto,

        @NotNull(message = "Valor de venda é obrigatório")
        @DecimalMin(
                value = "0.01",
                message = "Valor de venda deve ser maior que zero"
        )
        BigDecimal valorVenda

) {
}