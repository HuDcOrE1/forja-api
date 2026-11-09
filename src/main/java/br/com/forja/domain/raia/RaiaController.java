package br.com.forja.domain.raia;

import br.com.forja.domain.raia.dto.RaiaRequest;
import br.com.forja.domain.raia.dto.RaiaResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/raias")
@RequiredArgsConstructor
public class RaiaController {

    private final RaiaService service;

    @PreAuthorize(
            "hasAnyRole('ADMIN','GERENTE','ATENDENTE','INSTRUTOR')"
    )
    @GetMapping
    public List<RaiaResponse> listar() {

        return service.listar();
    }

    @PreAuthorize(
            "hasAnyRole('ADMIN','GERENTE','ATENDENTE','INSTRUTOR')"
    )
    @GetMapping("/{id}")
    public RaiaResponse buscarPorId(
            @PathVariable Long id
    ) {

        return service.buscarPorId(id);
    }

    @PreAuthorize(
            "hasAnyRole('ADMIN','GERENTE')"
    )
    @PostMapping
    public RaiaResponse salvar(
            @Valid
            @RequestBody RaiaRequest request
    ) {

        return service.salvar(request);
    }

    @PreAuthorize(
            "hasAnyRole('ADMIN','GERENTE')"
    )
    @PutMapping("/{id}")
    public RaiaResponse atualizar(
            @PathVariable Long id,

            @Valid
            @RequestBody RaiaRequest request
    ) {

        return service.atualizar(
                id,
                request
        );
    }

    @PreAuthorize(
            "hasRole('ADMIN')"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id
    ) {

        service.excluir(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}