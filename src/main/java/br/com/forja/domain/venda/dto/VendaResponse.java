package br.com.forja.domain.venda.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record VendaResponse(

        Long id,

        Long clienteId,

        String cliente,

        Long usuarioId,

        String usuario,

        BigDecimal valorTotal,

        String formaPagamento,

        String status,

        LocalDateTime criadoEm,

        List<ItemVendaResponse> itens

) {
}