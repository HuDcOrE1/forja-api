package br.com.forja.domain.cliente;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ClienteRepository
        extends JpaRepository<Cliente, Long>,
        JpaSpecificationExecutor<Cliente> {

    List<Cliente> findByAtivoTrue();

    // List<Cliente> findTop20ByAtivoTrueAndNomeContainingIgnoreCase(
    // String nome);

    // List<Cliente> findTop20ByAtivoTrueAndCpfContaining(
    // String cpf);

}