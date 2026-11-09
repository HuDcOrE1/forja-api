package br.com.forja.domain.estoque.dto;

import java.time.LocalDateTime;

public record MovimentacaoEstoqueResponse(

        Long id,

        Long produtoId,

        String produto,

        String tipo,

        Integer quantidade,

        String observacao,

        LocalDateTime criadoEm

) {
}