package br.com.forja.domain.relatorio.dto;

public record RelatorioEstoqueRequest(

                Long categoriaId,

                Integer estoqueMinimo,

                Boolean somenteAtivos

) {
}