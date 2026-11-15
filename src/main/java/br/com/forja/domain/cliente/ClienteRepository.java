package br.com.forja.domain.cliente;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository
        extends JpaRepository<Cliente, Long> {

    List<Cliente> findByAtivoTrue();

    List<Cliente> findTop20ByAtivoTrueAndNomeContainingIgnoreCase(
            String nome);

    List<Cliente> findTop20ByAtivoTrueAndCpfContaining(
            String cpf);

}