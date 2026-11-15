package br.com.forja.domain.produto;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository
        extends JpaRepository<Produto, Long> {

    List<Produto> findByAtivoTrue();

    List<Produto> findTop20ByAtivoTrueAndNomeContainingIgnoreCase(
            String nome);

    List<Produto> findTop20ByAtivoTrueAndCodigoContainingIgnoreCase(
            String codigo);

}