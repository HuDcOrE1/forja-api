package br.com.forja.domain.perfil;

import br.com.forja.domain.perfil.dto.PerfilResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/perfis")
@RequiredArgsConstructor
public class PerfilController {

    private final PerfilService service;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<PerfilResponse> listar() {

        return service.listar();

    }

}