package br.com.forja.domain.dashboard.dto;

import java.math.BigDecimal;

public record SaudeEstoqueResponse(

        Long produtosSemEstoque,

        Long produtosEstoqueCritico,

        Long produtosEstoqueSaudavel,

        Long totalProdutos,

        BigDecimal valorFinanceiroEstoque

) {
}