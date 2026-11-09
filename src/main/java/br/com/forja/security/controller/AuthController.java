package br.com.forja.security.controller;

import br.com.forja.domain.usuario.Usuario;
import br.com.forja.domain.usuario.UsuarioRepository;
import br.com.forja.security.dto.LoginRequest;
import br.com.forja.security.dto.LoginResponse;
import br.com.forja.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request
    ) {

        Usuario usuario =
                usuarioRepository
                        .findByEmail(request.email())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Usuário não encontrado"
                                ));

        if (!passwordEncoder.matches(
                request.senha(),
                usuario.getSenha()
        )) {

            throw new RuntimeException(
                    "Senha inválida"
            );
        }

        List<String> roles =
                usuario.getPerfis()
                        .stream()
                        .map(perfil ->
                                "ROLE_" + perfil.getNome()
                        )
                        .toList();

        String token =
                jwtService.gerarToken(
                        usuario.getEmail(),
                        roles
                );

        return ResponseEntity.ok(
                new LoginResponse(token)
        );
    }

    @GetMapping("/ping")
    public String ping() {

        return "FORJA OK";
    }
}