package br.com.forja.domain.venda;

import jakarta.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import br.com.forja.shared.search.TextoBusca;

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

                predicates.add(

                        cb.or(

                                TextoBusca.contemSemAcentos(
                                        cb,
                                        root.get("cliente").get("nome"),
                                        termo),

                                TextoBusca.contemSemAcentos(
                                        cb,
                                        root.get("usuario").get("nome"),
                                        termo)

                        )

                );

            }

            return cb.and(
                    predicates.toArray(
                            Predicate[]::new));

        };

    }

}