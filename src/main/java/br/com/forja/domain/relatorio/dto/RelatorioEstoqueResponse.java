package br.com.forja.domain.relatorio.dto;

import java.math.BigDecimal;

public record RelatorioEstoqueResponse(

        Long produtoId,

        String codigo,

        String produto,

        Integer estoque,

        BigDecimal valorVenda,

        BigDecimal valorTotalEstoque

) {
}