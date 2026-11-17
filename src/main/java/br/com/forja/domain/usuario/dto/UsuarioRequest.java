package br.com.forja.domain.usuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record UsuarioRequest(

        @NotBlank(message = "Nome é obrigatório") String nome,

        @NotBlank(message = "Email é obrigatório") @Email(message = "Email inválido") String email,

        String senha,

        Boolean ativo,

        @NotEmpty(message = "Ao menos um perfil deve ser informado") Set<Long> perfis

) {
}
