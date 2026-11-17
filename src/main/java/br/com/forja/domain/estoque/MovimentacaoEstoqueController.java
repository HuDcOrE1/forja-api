package br.com.forja.domain.estoque;

import br.com.forja.domain.estoque.dto.EntradaEstoqueRequest;
import br.com.forja.domain.estoque.dto.MovimentacaoEstoqueResponse;
import br.com.forja.domain.estoque.dto.SaidaEstoqueRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estoque")
@RequiredArgsConstructor
public class MovimentacaoEstoqueController {

        private final MovimentacaoEstoqueService service;

        @PreAuthorize("hasAnyRole('ADMIN','OPERADOR_ESTOQUE')")
        @PostMapping("/entrada")
        public MovimentacaoEstoqueResponse entrada(
                        @Valid @RequestBody EntradaEstoqueRequest request) {

                return service.entrada(request);
        }

        @PreAuthorize("hasAnyRole('ADMIN','OPERADOR_ESTOQUE')")
        @PostMapping("/saida")
        public MovimentacaoEstoqueResponse saida(
                        @Valid @RequestBody SaidaEstoqueRequest request) {

                return service.saida(request);
        }

        @PreAuthorize("hasAnyRole('ADMIN','GERENTE','OPERADOR_ESTOQUE')")
        @GetMapping("/historico")
        public Page<MovimentacaoEstoqueResponse> historico(

                        @RequestParam(defaultValue = "0") int page,

                        @RequestParam(defaultValue = "20") int size,

                        @RequestParam(required = false) Long produtoId,

                        @RequestParam(required = false) String tipo

        ) {

                return service.historico(
                                page,
                                size,
                                produtoId,
                                tipo);

        }

        @PreAuthorize("hasAnyRole('ADMIN','GERENTE','OPERADOR_ESTOQUE')")
        @GetMapping("/produto/{id}")
        public List<MovimentacaoEstoqueResponse> historicoProduto(
                        @PathVariable Long id) {

                return service.historicoProduto(id);
        }
}