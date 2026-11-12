package br.com.forja.domain.venda;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PagamentoVendaRepository
        extends JpaRepository<PagamentoVenda, Long> {

    List<PagamentoVenda> findByVendaId(Long vendaId);

}