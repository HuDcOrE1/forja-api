package br.com.forja.domain.venda;

import br.com.forja.domain.venda.dto.CancelarVendaRequest;
import br.com.forja.domain.venda.dto.VendaRequest;
import br.com.forja.domain.venda.dto.VendaResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendas")
@RequiredArgsConstructor
public class VendaController {

        private final VendaService service;

        @PreAuthorize("hasAnyRole('ADMIN','GERENTE','ATENDENTE')")
        @GetMapping
        public Page<VendaResponse> listar(

                        @RequestParam(defaultValue = "0") int page,

                        @RequestParam(defaultValue = "20") int size,

                        @RequestParam(required = false) Long clienteId,

                        @RequestParam(required = false) String status,

                        @RequestParam(required = false) String termo

        ) {

                return service.listar(
                                page,
                                size,
                                clienteId,
                                status,
                                termo);

        }

        @PreAuthorize("hasAnyRole('ADMIN','GERENTE','ATENDENTE')")
        @GetMapping("/{id}")
        public VendaResponse buscarPorId(
                        @PathVariable Long id) {

                return service.buscarPorId(id);
        }

        @PreAuthorize("hasAnyRole('ADMIN','GERENTE','ATENDENTE')")
        @PostMapping
        public VendaResponse salvar(
                        @Valid @RequestBody VendaRequest request) {

                return service.salvar(request);
        }

        @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
        @PatchMapping("/{id}/cancelar")
        public VendaResponse cancelar(
                        @PathVariable Long id,
                        @Valid @RequestBody CancelarVendaRequest request) {

                return service.cancelar(id, request);
        }
}