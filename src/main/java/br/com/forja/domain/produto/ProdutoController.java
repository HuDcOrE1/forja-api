package br.com.forja.domain.produto;

import br.com.forja.domain.produto.dto.ProdutoRequest;
import br.com.forja.domain.produto.dto.ProdutoResponse;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;

import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {

        private final ProdutoService service;

        @PreAuthorize("hasAnyRole('ADMIN','GERENTE','ATENDENTE')")
        @GetMapping
        public Page<ProdutoResponse> listar(

                        @RequestParam(defaultValue = "0") int page,

                        @RequestParam(defaultValue = "20") int size,

                        @RequestParam(required = false) String termo,

                        @RequestParam(required = false) StatusEstoque statusEstoque

        ) {

                return service.listar(
                                page,
                                size,
                                termo,
                                statusEstoque);

        }

        @PreAuthorize("hasAnyRole('ADMIN','GERENTE','ATENDENTE')")
        @GetMapping("/{id}")
        public ProdutoResponse buscarPorId(
                        @PathVariable Long id) {

                return service.buscarPorId(id);

        }

        @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
        @PostMapping
        public ProdutoResponse salvar(
                        @Valid @RequestBody ProdutoRequest request) {

                return service.salvar(
                                request);

        }

        @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
        @PutMapping("/{id}")
        public ProdutoResponse atualizar(
                        @PathVariable Long id,

                        @Valid @RequestBody ProdutoRequest request) {

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