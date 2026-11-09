package br.com.forja.domain.dashboard.dto;

import java.math.BigDecimal;

public record ReceitaFormaPagamentoResponse(

        String formaPagamento,

        BigDecimal valor

) {
}