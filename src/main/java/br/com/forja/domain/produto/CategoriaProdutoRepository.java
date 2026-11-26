package br.com.forja.domain.produto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CategoriaProdutoRepository
                extends JpaRepository<CategoriaProduto, Long>,
                JpaSpecificationExecutor<CategoriaProduto> {
}