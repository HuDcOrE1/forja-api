package br.com.forja.domain.venda.dto;

import br.com.forja.domain.venda.FormaPagamento;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PagamentoVendaRequest(

        @NotNull(message = "Forma de pagamento é obrigatória") FormaPagamento formaPagamento,

        @NotNull(message = "Valor é obrigatório") BigDecimal valor

) {
}