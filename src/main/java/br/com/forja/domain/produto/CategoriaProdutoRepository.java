package br.com.forja.domain.produto;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaProdutoRepository
                extends JpaRepository<CategoriaProduto, Long> {
}