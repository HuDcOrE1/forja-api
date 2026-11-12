package br.com.forja.domain.venda.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record VendaRequest(

        @NotNull(message = "Cliente é obrigatório") Long clienteId,

        BigDecimal valorDesconto,

        BigDecimal valorAcrescimo,

        @NotEmpty(message = "A venda deve possuir pelo menos um item") List<ItemVendaRequest> itens,

        @Valid @NotEmpty(message = "A venda deve possuir ao menos uma forma de pagamento") List<PagamentoVendaRequest> pagamentos

) {
}