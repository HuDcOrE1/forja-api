package br.com.forja.domain.agendamento.dto;

import java.time.LocalDateTime;

public record AgendamentoResponse(

                Long id,

                Long clienteId,

                String cliente,

                Long raiaId,

                String raia,

                LocalDateTime dataInicio,

                LocalDateTime dataFim,

                String status,

                String observacao

) {
}