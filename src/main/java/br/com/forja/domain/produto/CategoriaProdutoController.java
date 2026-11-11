package br.com.forja.domain.produto;

import br.com.forja.domain.produto.dto.CategoriaProdutoRequest;
import br.com.forja.domain.produto.dto.CategoriaProdutoResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias-produto")
@RequiredArgsConstructor
public class CategoriaProdutoController {

    private final CategoriaProdutoService service;

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','ATENDENTE')")
    @GetMapping
    public List<CategoriaProdutoResponse> listar() {

        return service.listar();
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','ATENDENTE')")
    @GetMapping("/{id}")
    public CategoriaProdutoResponse buscarPorId(
            @PathVariable Long id) {

        return service.buscarPorId(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @PostMapping
    public CategoriaProdutoResponse salvar(
            @Valid @RequestBody CategoriaProdutoRequest request) {

        return service.salvar(request);
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @PutMapping("/{id}")
    public CategoriaProdutoResponse atualizar(
            @PathVariable Long id,

            @Valid @RequestBody CategoriaProdutoRequest request) {

        return service.atualizar(
                id,
                request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id) {

        service.excluir(id);

        return ResponseEntity
                .noContent()
                .build();
    }

}