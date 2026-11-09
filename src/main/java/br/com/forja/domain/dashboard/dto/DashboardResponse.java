package br.com.forja.domain.dashboard.dto;

import java.math.BigDecimal;

public record DashboardResponse(

        Long clientesAtivos,

        Long produtosAtivos,

        Long totalVendas,

        BigDecimal valorTotalVendas,

        BigDecimal ticketMedio,

        String produtoMaisVendido,

        Long quantidadeProdutoMaisVendido,

        String clienteMaisComprador,

        Long quantidadeComprasCliente,

        BigDecimal receitaHoje,

        BigDecimal receitaMes,

        Long agendamentosAtivos,

        String raiaMaisUtilizada,

        Long quantidadeAgendamentosRaia,

        Long raiasAtivas,

        BigDecimal valorTotalEstoque

) {
}