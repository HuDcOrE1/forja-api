package br.com.forja.domain.produto;

import br.com.forja.domain.produto.dto.ProdutoRequest;
import br.com.forja.domain.produto.dto.ProdutoResponse;
import br.com.forja.exception.ProdutoNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

        private final ProdutoRepository repository;
        private final CategoriaProdutoRepository categoriaRepository;

        public List<ProdutoResponse> listar() {

                return repository.findByAtivoTrue()
                                .stream()
                                .map(this::toResponse)
                                .toList();
        }

        public ProdutoResponse buscarPorId(Long id) {

                Produto produto = repository.findById(id)
                                .orElseThrow(
                                                () -> new ProdutoNaoEncontradoException(id));

                return toResponse(produto);
        }

        public ProdutoResponse salvar(
                        ProdutoRequest request) {

                CategoriaProduto categoria = categoriaRepository.findById(
                                request.categoriaId()).orElseThrow();

                Produto produto = Produto.builder()
                                .codigo(request.codigo())
                                .nome(request.nome())
                                .descricao(request.descricao())
                                .categoria(categoria)
                                .marca(request.marca())
                                .unidadeMedida(
                                                request.unidadeMedida())
                                .estoqueAtual(
                                                request.estoqueAtual())
                                .estoqueMinimo(
                                                request.estoqueMinimo())
                                .valorCusto(
                                                request.valorCusto())
                                .valorVenda(
                                                request.valorVenda())
                                .ativo(
                                                request.ativo() != null
                                                                ? request.ativo()
                                                                : true)
                                .build();

                produto = repository.save(produto);

                return toResponse(produto);
        }

        public ProdutoResponse atualizar(
                        Long id,
                        ProdutoRequest request) {

                Produto produto = repository.findById(id)
                                .orElseThrow(
                                                () -> new ProdutoNaoEncontradoException(id));

                CategoriaProduto categoria = categoriaRepository.findById(
                                request.categoriaId()).orElseThrow();

                produto.setCodigo(
                                request.codigo());

                produto.setNome(
                                request.nome());

                produto.setDescricao(
                                request.descricao());

                produto.setCategoria(
                                categoria);

                produto.setMarca(
                                request.marca());

                produto.setUnidadeMedida(
                                request.unidadeMedida());

                produto.setEstoqueAtual(
                                request.estoqueAtual());

                produto.setEstoqueMinimo(
                                request.estoqueMinimo());

                produto.setValorCusto(
                                request.valorCusto());

                produto.setValorVenda(
                                request.valorVenda());

                produto.setAtivo(
                                request.ativo());

                produto = repository.save(produto);

                return toResponse(produto);
        }

        public void excluir(Long id) {

                Produto produto = repository.findById(id)
                                .orElseThrow(
                                                () -> new ProdutoNaoEncontradoException(id));

                produto.setAtivo(false);

                repository.save(produto);
        }

        private ProdutoResponse toResponse(
                        Produto produto) {

                return new ProdutoResponse(

                                produto.getId(),

                                produto.getCodigo(),

                                produto.getNome(),

                                produto.getDescricao(),

                                produto.getCategoria().getId(),

                                produto.getCategoria().getNome(),

                                produto.getMarca(),

                                produto.getUnidadeMedida(),

                                produto.getEstoqueAtual(),

                                produto.getEstoqueMinimo(),

                                produto.getValorCusto(),

                                produto.getValorVenda(),

                                produto.getAtivo());
        }

}