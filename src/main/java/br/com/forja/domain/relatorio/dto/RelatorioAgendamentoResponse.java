package br.com.forja.domain.relatorio.dto;

import java.time.LocalDateTime;

public record RelatorioAgendamentoResponse(

        Long id,

        String cliente,

        String raia,

        LocalDateTime inicio,

        LocalDateTime fim,

        String status

) {
}