package br.com.forja.domain.estoque;

import br.com.forja.domain.estoque.dto.EntradaEstoqueRequest;
import br.com.forja.domain.estoque.dto.MovimentacaoEstoqueResponse;
import br.com.forja.domain.estoque.dto.SaidaEstoqueRequest;
import br.com.forja.domain.produto.Produto;
import br.com.forja.domain.produto.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovimentacaoEstoqueService {

        private final MovimentacaoEstoqueRepository repository;
        private final ProdutoRepository produtoRepository;

        public MovimentacaoEstoqueResponse entrada(
                        EntradaEstoqueRequest request) {

                Produto produto = produtoRepository.findById(
                                request.produtoId()).orElseThrow();

                produto.setEstoqueAtual(
                                produto.getEstoqueAtual()
                                                + request.quantidade());

                produtoRepository.save(produto);

                MovimentacaoEstoque movimentacao = MovimentacaoEstoque.builder()
                                .produto(produto)
                                .tipo("ENTRADA")
                                .quantidade(
                                                request.quantidade())
                                .observacao(
                                                request.observacao())
                                .criadoEm(
                                                LocalDateTime.now())
                                .build();

                movimentacao = repository.save(
                                movimentacao);

                return toResponse(
                                movimentacao);
        }

        public MovimentacaoEstoqueResponse saida(
                        SaidaEstoqueRequest request) {

                Produto produto = produtoRepository.findById(
                                request.produtoId()).orElseThrow();

                if (produto.getEstoqueAtual() < request.quantidade()) {

                        throw new RuntimeException(
                                        "Estoque insuficiente.");
                }

                produto.setEstoqueAtual(
                                produto.getEstoqueAtual()
                                                - request.quantidade());

                produtoRepository.save(produto);

                MovimentacaoEstoque movimentacao = MovimentacaoEstoque.builder()
                                .produto(produto)
                                .tipo("SAIDA")
                                .quantidade(
                                                request.quantidade())
                                .observacao(
                                                request.observacao())
                                .criadoEm(
                                                LocalDateTime.now())
                                .build();

                movimentacao = repository.save(
                                movimentacao);

                return toResponse(
                                movimentacao);
        }

        public List<MovimentacaoEstoqueResponse> historico() {

                return repository.findAll()
                                .stream()
                                .map(this::toResponse)
                                .toList();
        }

        public List<MovimentacaoEstoqueResponse> historicoProduto(
                        Long produtoId) {

                return repository
                                .findByProdutoIdOrderByCriadoEmDesc(
                                                produtoId)
                                .stream()
                                .map(this::toResponse)
                                .toList();
        }

        private MovimentacaoEstoqueResponse toResponse(
                        MovimentacaoEstoque movimentacao) {

                return new MovimentacaoEstoqueResponse(

                                movimentacao.getId(),

                                movimentacao.getProduto().getId(),

                                movimentacao.getProduto().getNome(),

                                movimentacao.getTipo(),

                                movimentacao.getQuantidade(),

                                movimentacao.getObservacao(),

                                movimentacao.getCriadoEm());
        }

}