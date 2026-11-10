package br.com.forja.domain.cliente;

import br.com.forja.domain.cliente.dto.ClienteRequest;
import br.com.forja.domain.cliente.dto.ClienteResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService service;

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','ATENDENTE')")
    @GetMapping
    public List<ClienteResponse> listar() {

        return service.listar();
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','ATENDENTE')")
    @GetMapping("/{id}")
    public ClienteResponse buscarPorId(
            @PathVariable Long id) {

        return service.buscarPorId(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','ATENDENTE')")
    @PostMapping
    public ClienteResponse salvar(
            @Valid @RequestBody ClienteRequest request) {

        return service.salvar(request);
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @PutMapping("/{id}")
    public ClienteResponse atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ClienteRequest request) {

        return service.atualizar(id, request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id) {

        service.excluir(id);

        return ResponseEntity.noContent().build();
    }
}