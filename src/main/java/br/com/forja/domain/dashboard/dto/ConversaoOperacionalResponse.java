package br.com.forja.domain.dashboard.dto;

public record ConversaoOperacionalResponse(

        Long agendados,

        Long finalizados,

        Long cancelados,

        Double taxaConversao,

        Double taxaCancelamento

) {
}