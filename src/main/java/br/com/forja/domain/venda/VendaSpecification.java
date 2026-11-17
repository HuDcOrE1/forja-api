package br.com.forja.domain.venda;

import jakarta.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class VendaSpecification {

    private VendaSpecification() {
    }

    public static Specification<Venda> filtrar(
            Long clienteId,
            String status,
            String termo) {

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (clienteId != null) {

                predicates.add(
                        cb.equal(
                                root.get("cliente")
                                        .get("id"),
                                clienteId));

            }

            if (status != null
                    && !status.isBlank()) {

                predicates.add(
                        cb.equal(
                                root.get("status"),
                                StatusVenda.valueOf(
                                        status)));

            }

            if (termo != null
                    && !termo.isBlank()) {

                String pesquisa = "%"
                        + termo.toLowerCase()
                        + "%";

                predicates.add(

                        cb.or(

                                cb.like(
                                        cb.lower(
                                                root.get("cliente")
                                                        .get("nome")),
                                        pesquisa),

                                cb.like(
                                        cb.lower(
                                                root.get("usuario")
                                                        .get("nome")),
                                        pesquisa)

                        )

                );

            }

            return cb.and(
                    predicates.toArray(
                            Predicate[]::new));

        };

    }

}