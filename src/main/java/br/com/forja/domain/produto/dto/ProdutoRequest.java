package br.com.forja.domain.produto.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record ProdutoRequest(

        @NotBlank(message = "Código é obrigatório") String codigo,

        @NotBlank(message = "Nome é obrigatório") String nome,

        String descricao,

        @NotNull(message = "Categoria é obrigatória") Long categoriaId,

        String marca,

        String unidadeMedida,

        @NotNull(message = "Estoque atual é obrigatório") @PositiveOrZero(message = "Estoque atual não pode ser negativo") Integer estoqueAtual,

        @NotNull(message = "Estoque mínimo é obrigatório") @PositiveOrZero(message = "Estoque mínimo não pode ser negativo") Integer estoqueMinimo,

        @NotNull(message = "Valor de custo é obrigatório") @DecimalMin(value = "0.01", message = "Valor de custo deve ser maior que zero") BigDecimal valorCusto,

        @NotNull(message = "Valor de venda é obrigatório") @DecimalMin(value = "0.01", message = "Valor de venda deve ser maior que zero") BigDecimal valorVenda,

        Boolean ativo

) {
}