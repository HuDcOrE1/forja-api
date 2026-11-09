package br.com.forja.domain.dashboard.dto;

import java.math.BigDecimal;

public record TicketMedioPeriodoResponse(

        String periodo,

        BigDecimal ticketMedio

) {
}