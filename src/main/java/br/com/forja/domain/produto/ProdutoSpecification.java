package br.com.forja.domain.produto;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class ProdutoSpecification {

    private ProdutoSpecification() {
    }

    public static Specification<Produto> filtrar(
            String termo,
            StatusEstoque statusEstoque) {

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            predicates.add(
                    cb.isTrue(
                            root.get("ativo")));

            if (termo != null
                    && !termo.isBlank()) {

                Predicate nome = cb.like(
                        cb.lower(
                                root.get("nome")),
                        "%"
                                + termo
                                        .toLowerCase()
                                + "%");

                Predicate codigo = cb.like(
                        cb.lower(
                                root.get("codigo")),
                        "%"
                                + termo
                                        .toLowerCase()
                                + "%");

                predicates.add(
                        cb.or(
                                nome,
                                codigo));
            }

            if (statusEstoque != null) {

                switch (statusEstoque) {

                    case SEM_ESTOQUE -> predicates.add(
                            cb.lessThanOrEqualTo(
                                    root.get("estoqueAtual"),
                                    0));

                    case CRITICO -> predicates.add(
                            cb.and(
                                    cb.greaterThan(
                                            root.get("estoqueAtual"),
                                            0),
                                    cb.lessThanOrEqualTo(
                                            root.get("estoqueAtual"),
                                            root.get("estoqueMinimo"))));

                    case SAUDAVEL -> predicates.add(
                            cb.greaterThan(
                                    root.get("estoqueAtual"),
                                    root.get("estoqueMinimo")));
                }

            }

            return cb.and(
                    predicates.toArray(
                            Predicate[]::new));

        };
    }

}