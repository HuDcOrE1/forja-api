package br.com.forja.domain.venda.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record VendaRequest(

        @NotNull (message = "Cliente é obrigatório")
        Long clienteId,

        @NotBlank(message = "Forma de pagamento é obrigatória")
        String formaPagamento,

        @NotEmpty(message = "A venda deve possuir pelo menos um item")
        List<ItemVendaRequest> itens

) {
}