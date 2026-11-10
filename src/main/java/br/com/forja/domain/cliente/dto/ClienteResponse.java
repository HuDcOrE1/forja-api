package br.com.forja.domain.cliente.dto;

public record ClienteResponse(

        Long id,

        String nome,

        String cpf,

        String telefone,

        String email,

        String cr,

        Boolean ativo,

        String cep,

        String logradouro,

        String numero,

        String complemento,

        String bairro,

        String cidade,

        String uf

) {
}