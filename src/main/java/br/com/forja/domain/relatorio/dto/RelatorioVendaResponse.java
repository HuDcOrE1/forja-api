package br.com.forja.domain.relatorio.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RelatorioVendaResponse(

                Long vendaId,

                String cliente,

                String usuario,

                BigDecimal valorTotal,

                String formaPagamento,

                String status,

                LocalDateTime dataVenda

) {
}