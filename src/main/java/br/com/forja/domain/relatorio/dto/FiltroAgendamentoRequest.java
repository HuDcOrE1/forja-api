package br.com.forja.domain.relatorio.dto;

public record FiltroAgendamentoRequest(
                Long clienteId,
                Long raiaId,
                String status) {
}