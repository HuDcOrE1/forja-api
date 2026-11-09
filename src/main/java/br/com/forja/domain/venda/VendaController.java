package br.com.forja.domain.venda;

import br.com.forja.domain.venda.dto.VendaRequest;
import br.com.forja.domain.venda.dto.VendaResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendas")
@RequiredArgsConstructor
public class VendaController {

    private final VendaService service;

    @PreAuthorize(
            "hasAnyRole('ADMIN','GERENTE','ATENDENTE')"
    )
    @GetMapping
    public List<VendaResponse> listar() {

        return service.listar();
    }

    @PreAuthorize(
            "hasAnyRole('ADMIN','GERENTE','ATENDENTE')"
    )
    @GetMapping("/{id}")
    public VendaResponse buscarPorId(
            @PathVariable Long id
    ) {

        return service.buscarPorId(id);
    }

    @PreAuthorize(
            "hasAnyRole('ADMIN','GERENTE','ATENDENTE')"
    )
    @PostMapping
    public VendaResponse salvar(
            @Valid
            @RequestBody VendaRequest request
    ) {

        return service.salvar(request);
    }
}