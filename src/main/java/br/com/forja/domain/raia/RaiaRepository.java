package br.com.forja.domain.raia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface RaiaRepository
        extends JpaRepository<Raia, Long>,
        JpaSpecificationExecutor<Raia> {

    List<Raia> findByAtivaTrue();
}