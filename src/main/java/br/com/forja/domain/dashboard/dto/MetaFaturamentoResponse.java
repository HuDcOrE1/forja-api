package br.com.forja.domain.dashboard.dto;

import java.math.BigDecimal;

public record MetaFaturamentoResponse(

                BigDecimal meta,

                BigDecimal realizado,

                Double percentualAtingido

) {
}