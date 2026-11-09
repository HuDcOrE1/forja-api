package br.com.forja.domain.dashboard.dto;

public record MetaAgendamentoResponse(

        Long meta,

        Long realizado,

        Double percentualAtingido

) {
}