package br.com.forja.domain.relatorio.dto;

import java.time.LocalDateTime;

public record RelatorioVendaRequest(

        Long clienteId,

        String formaPagamento,

        String status,

        LocalDateTime inicio,

        LocalDateTime fim

) {
}