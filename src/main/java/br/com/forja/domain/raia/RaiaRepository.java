package br.com.forja.domain.raia;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RaiaRepository
        extends JpaRepository<Raia, Long> {

    List<Raia> findByAtivaTrue();
}