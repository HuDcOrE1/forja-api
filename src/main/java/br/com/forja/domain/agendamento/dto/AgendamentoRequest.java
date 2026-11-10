package br.com.forja.domain.agendamento.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AgendamentoRequest(

                @NotNull(message = "Cliente é obrigatório") Long clienteId,

                @NotNull(message = "Raia é obrigatória") Long raiaId,

                @NotNull(message = "Data início é obrigatória") LocalDateTime dataInicio,

                @NotNull(message = "Data fim é obrigatória") LocalDateTime dataFim,

                String observacao

) {
}