package br.com.forja.domain.dashboard.dto;

import java.math.BigDecimal;

public record FaturamentoMensalResponse(

                String mes,

                BigDecimal valor

) {
}