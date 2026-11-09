package br.com.forja.domain.agendamento;

import br.com.forja.domain.agendamento.dto.AgendamentoRequest;
import br.com.forja.domain.agendamento.dto.AgendamentoResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agendamentos")
@RequiredArgsConstructor
public class AgendamentoController {

    private final AgendamentoService service;

    @PreAuthorize(
            "hasAnyRole('ADMIN','GERENTE','ATENDENTE','INSTRUTOR')"
    )
    @GetMapping
    public List<AgendamentoResponse> listar() {

        return service.listar();
    }

    @PreAuthorize(
            "hasAnyRole('ADMIN','GERENTE','ATENDENTE','INSTRUTOR')"
    )
    @GetMapping("/{id}")
    public AgendamentoResponse buscarPorId(
            @PathVariable Long id
    ) {

        return service.buscarPorId(id);
    }

    @PreAuthorize(
            "hasAnyRole('ADMIN','GERENTE','ATENDENTE')"
    )
    @PostMapping
    public AgendamentoResponse salvar(
            @Valid
            @RequestBody AgendamentoRequest request
    ) {

        return service.salvar(request);
    }

    @PreAuthorize(
            "hasAnyRole('ADMIN','GERENTE','ATENDENTE')"
    )
    @PutMapping("/{id}")
    public AgendamentoResponse atualizar(
            @PathVariable Long id,
            @Valid
            @RequestBody AgendamentoRequest request
    ) {

        return service.atualizar(id, request);
    }

    @PreAuthorize(
            "hasAnyRole('ADMIN','GERENTE','ATENDENTE')"
    )
    @PutMapping("/{id}/cancelar")
    public void cancelar(
            @PathVariable Long id
    ) {

        service.cancelar(id);
    }

    @PreAuthorize(
            "hasAnyRole('ADMIN','GERENTE','INSTRUTOR')"
    )
    @PutMapping("/{id}/finalizar")
    public void finalizar(
            @PathVariable Long id
    ) {

        service.finalizar(id);
    }
}