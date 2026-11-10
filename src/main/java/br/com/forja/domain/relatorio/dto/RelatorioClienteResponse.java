package br.com.forja.domain.relatorio.dto;

public record RelatorioClienteResponse(

                Long totalClientes,

                Long clientesAtivos,

                Long clientesInativos

) {
}