package br.com.forja.domain.venda;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface VendaRepository
        extends JpaRepository<Venda, Long>,
        JpaSpecificationExecutor<Venda> {

    List<Venda> findAllByOrderByCriadoEmDesc();

}