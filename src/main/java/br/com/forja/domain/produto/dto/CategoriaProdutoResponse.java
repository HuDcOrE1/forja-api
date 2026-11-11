package br.com.forja.domain.produto.dto;

public record CategoriaProdutoResponse(

        Long id,

        String nome,

        String descricao,

        Boolean ativo

) {
}