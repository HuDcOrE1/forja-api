package br.com.forja.domain.raia.dto;

public record RaiaResponse(

        Long id,

        Integer numero,

        String descricao,

        Boolean ativa

) {
}