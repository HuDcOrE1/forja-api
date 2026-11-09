package br.com.forja.domain.dashboard.dto;

public record MetaVendaResponse(

        Long meta,

        Long realizado,

        Double percentualAtingido

) {
}