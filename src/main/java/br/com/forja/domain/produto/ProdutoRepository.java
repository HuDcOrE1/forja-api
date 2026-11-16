package br.com.forja.domain.produto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProdutoRepository
        extends JpaRepository<Produto, Long>,
        JpaSpecificationExecutor<Produto> {

    List<Produto> findAllByAtivoTrue();

    List<Produto> findByAtivoTrue();
}