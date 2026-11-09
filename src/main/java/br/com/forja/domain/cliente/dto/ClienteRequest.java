package br.com.forja.domain.cliente.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClienteRequest(

        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @NotBlank(message = "CPF é obrigatório")
        @Size(
        min = 11,
        max = 11,
        message = "CPF deve possuir 11 dígitos"
        )
        String cpf,

        @NotBlank(message = "Telefone é obrigatório")
        @Size(
        max = 30,
        message = "Telefone inválido"
        )
        String telefone,

        @Email(message = "Email inválido")
        @NotBlank(message = "Email é obrigatório")
        String email,

        String cr

) {
}