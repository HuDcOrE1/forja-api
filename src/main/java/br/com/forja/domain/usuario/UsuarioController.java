package br.com.forja.domain.usuario;

import br.com.forja.domain.usuario.dto.UsuarioRequest;
import br.com.forja.domain.usuario.dto.UsuarioResponse;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<UsuarioResponse> listar() {

        return service.listar();

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public UsuarioResponse buscarPorId(
            @PathVariable Long id) {

        return service.buscarPorId(id);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public UsuarioResponse salvar(
            @Valid @RequestBody UsuarioRequest request) {

        return service.salvar(
                request);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public UsuarioResponse atualizar(
            @PathVariable Long id,

            @Valid @RequestBody UsuarioRequest request) {

        return service.atualizar(
                id,
                request);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inativar(
            @PathVariable Long id) {

        service.inativar(id);

        return ResponseEntity
                .noContent()
                .build();

    }

}