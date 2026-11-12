package br.com.forja.domain.venda.dto;

import br.com.forja.domain.venda.FormaPagamento;

import java.math.BigDecimal;

public record PagamentoVendaResponse(

        FormaPagamento formaPagamento,

        BigDecimal valor

) {
}
