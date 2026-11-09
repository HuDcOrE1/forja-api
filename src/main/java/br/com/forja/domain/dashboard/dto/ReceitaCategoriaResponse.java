package br.com.forja.domain.dashboard.dto;

import java.math.BigDecimal;

public record ReceitaCategoriaResponse(

        String categoria,

        BigDecimal valor

) {
}