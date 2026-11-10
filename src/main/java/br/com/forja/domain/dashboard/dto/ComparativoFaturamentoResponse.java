package br.com.forja.domain.dashboard.dto;

import java.math.BigDecimal;

public record ComparativoFaturamentoResponse(

                BigDecimal faturamentoMesAtual,

                BigDecimal faturamentoMesAnterior,

                BigDecimal diferenca,

                Double crescimentoPercentual

) {
}