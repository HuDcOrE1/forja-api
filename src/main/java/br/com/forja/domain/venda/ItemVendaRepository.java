package br.com.forja.domain.venda;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemVendaRepository
                extends JpaRepository<ItemVenda, Long> {

        List<ItemVenda> findByVendaId(
                        Long vendaId);
}