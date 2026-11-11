package br.com.forja.domain.produto.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoriaProdutoRequest(

        @NotBlank(message = "Nome é obrigatório") String nome,

        String descricao,

        Boolean ativo

) {
}