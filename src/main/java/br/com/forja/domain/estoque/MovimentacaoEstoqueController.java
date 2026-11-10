package br.com.forja.domain.estoque;

import br.com.forja.domain.estoque.dto.EntradaEstoqueRequest;
import br.com.forja.domain.estoque.dto.MovimentacaoEstoqueResponse;
import br.com.forja.domain.estoque.dto.SaidaEstoqueRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
        public List<MovimentacaoEstoqueResponse> historico() {

                return service.historico();
        }

        @PreAuthorize("hasAnyRole('ADMIN','GERENTE','OPERADOR_ESTOQUE')")
        @GetMapping("/produto/{id}")
        public List<MovimentacaoEstoqueResponse> historicoProduto(
                        @PathVariable Long id) {

                return service.historicoProduto(id);
        }
}