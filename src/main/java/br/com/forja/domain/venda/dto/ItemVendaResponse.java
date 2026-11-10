package br.com.forja.domain.venda.dto;

import java.math.BigDecimal;

public record ItemVendaResponse(

                Long produtoId,

                String produto,

                Integer quantidade,

                BigDecimal valorUnitario,

                BigDecimal subtotal

) {
}