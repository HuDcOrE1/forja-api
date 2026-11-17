package br.com.forja.domain.estoque;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MovimentacaoEstoqueRepository
                extends JpaRepository<MovimentacaoEstoque, Long>,
                JpaSpecificationExecutor<MovimentacaoEstoque> {

        List<MovimentacaoEstoque> findByProdutoIdOrderByCriadoEmDesc(
                        Long produtoId);
}