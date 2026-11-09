package br.com.forja.domain.produto.dto;

import java.math.BigDecimal;

public record ProdutoResponse(

        Long id,

        String codigo,

        String nome,

        String descricao,

        Long categoriaId,

        String categoriaNome,

        Integer estoque,

        BigDecimal valorCusto,

        BigDecimal valorVenda,

        Boolean ativo

) {
}