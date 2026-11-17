package br.com.forja.domain.estoque;

import jakarta.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class MovimentacaoEstoqueSpecification {

    private MovimentacaoEstoqueSpecification() {
    }

    public static Specification<MovimentacaoEstoque> filtrar(
            Long produtoId,
            String tipo) {

        return (
                root,
                query,
                cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (produtoId != null) {

                predicates.add(
                        cb.equal(
                                root.get("produto")
                                        .get("id"),
                                produtoId));

            }

            if (tipo != null
                    && !tipo.isBlank()) {

                predicates.add(
                        cb.equal(
                                root.get("tipo"),
                                tipo));

            }

            return cb.and(
                    predicates.toArray(
                            Predicate[]::new));

        };

    }

}