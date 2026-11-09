package br.com.forja.security;

import br.com.forja.domain.perfil.Perfil;
import br.com.forja.domain.usuario.Usuario;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.stream.Collectors;

public class UserDetailsImpl {

    private UserDetailsImpl() {
    }

    public static User build(
            Usuario usuario
    ) {

        return new User(

                usuario.getEmail(),

                usuario.getSenha(),

                usuario.getPerfis()
                        .stream()
                        .map(Perfil::getNome)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList())
        );
    }
}