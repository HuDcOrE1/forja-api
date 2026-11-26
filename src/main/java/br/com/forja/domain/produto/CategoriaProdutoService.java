package br.com.forja.domain.produto;

import br.com.forja.domain.produto.dto.CategoriaProdutoRequest;
import br.com.forja.domain.produto.dto.CategoriaProdutoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaProdutoService {

    private final CategoriaProdutoRepository repository;

    public Page<CategoriaProdutoResponse> listar(int page, int size, String termo) {

        Pageable pageable = PageRequest.of(page, size);

        return repository.findAll(
                CategoriaProdutoSpecification.filtrar(termo),
                pageable)
                .map(this::toResponse);
    }

    public List<CategoriaProdutoResponse> listarOpcoes() {

        return repository.findAll()
                .stream()
                .filter(categoria -> Boolean.TRUE.equals(
                        categoria.getAtivo()))
                .map(this::toResponse)
                .toList();
    }

    public CategoriaProdutoResponse buscarPorId(
            Long id) {

        CategoriaProduto categoria = repository.findById(id)
                .orElseThrow();

        return toResponse(categoria);
    }

    public CategoriaProdutoResponse salvar(
            CategoriaProdutoRequest request) {

        CategoriaProduto categoria = CategoriaProduto.builder()
                .nome(
                        request.nome())
                .descricao(
                        request.descricao())
                .ativo(
                        request.ativo() != null
                                ? request.ativo()
                                : true)
                .criadoEm(
                        LocalDateTime.now())
                .build();

        categoria = repository.save(categoria);

        return toResponse(categoria);
    }

    public CategoriaProdutoResponse atualizar(
            Long id,
            CategoriaProdutoRequest request) {

        CategoriaProduto categoria = repository.findById(id)
                .orElseThrow();

        categoria.setNome(
                request.nome());

        categoria.setDescricao(
                request.descricao());

        categoria.setAtivo(
                request.ativo());

        categoria = repository.save(categoria);

        return toResponse(categoria);
    }

    public void excluir(
            Long id) {

        CategoriaProduto categoria = repository.findById(id)
                .orElseThrow();

        categoria.setAtivo(false);

        repository.save(categoria);
    }

    private CategoriaProdutoResponse toResponse(
            CategoriaProduto categoria) {

        return new CategoriaProdutoResponse(

                categoria.getId(),

                categoria.getNome(),

                categoria.getDescricao(),

                categoria.getAtivo()

        );
    }

}