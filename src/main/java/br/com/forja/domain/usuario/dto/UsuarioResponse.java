package br.com.forja.domain.usuario.dto;

import java.util.Set;

public record UsuarioResponse(

        Long id,

        String nome,

        String email,

        Boolean ativo,

        Set<String> perfis

) {
}