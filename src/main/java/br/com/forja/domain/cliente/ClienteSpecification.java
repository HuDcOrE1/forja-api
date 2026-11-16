package br.com.forja.domain.cliente;

import jakarta.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ClienteSpecification {

    private ClienteSpecification() {
    }

    public static Specification<Cliente> filtrar(
            String termo,
            Boolean ativo) {

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (ativo != null) {

                predicates.add(
                        cb.equal(
                                root.get("ativo"),
                                ativo));

            }

            if (termo != null
                    && !termo.isBlank()) {

                String termoPesquisa = "%" +
                        termo.trim()
                                .toLowerCase()
                        + "%";

                Predicate nome = cb.like(
                        cb.lower(
                                root.get("nome")),
                        termoPesquisa);

                Predicate cpf = cb.like(
                        root.get("cpf"),
                        termo.trim());

                Predicate email = cb.like(
                        cb.lower(
                                root.get("email")),
                        termoPesquisa);

                Predicate cr = cb.like(
                        cb.lower(
                                root.get("cr")),
                        termoPesquisa);

                predicates.add(
                        cb.or(
                                nome,
                                cpf,
                                email,
                                cr));

            }

            return cb.and(
                    predicates.toArray(
                            Predicate[]::new));

        };

    }

}