package br.com.forja.domain.agendamento;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AgendamentoRepository
        extends JpaRepository<Agendamento, Long> {

    List<Agendamento> findByRaiaId(
            Long raiaId
    );

    List<Agendamento> findByDataInicioBetween(
            LocalDateTime inicio,
            LocalDateTime fim
    );
}