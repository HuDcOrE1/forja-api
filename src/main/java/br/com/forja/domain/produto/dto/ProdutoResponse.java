package br.com.forja.domain.produto.dto;

import java.math.BigDecimal;

public record ProdutoResponse(

        Long id,

        String codigo,

        String nome,

        String descricao,

        Long categoriaId,

        String categoriaNome,

        String marca,

        String unidadeMedida,

        Integer estoqueAtual,

        Integer estoqueMinimo,

        BigDecimal valorCusto,

        BigDecimal valorVenda,

        Boolean ativo

) {
}