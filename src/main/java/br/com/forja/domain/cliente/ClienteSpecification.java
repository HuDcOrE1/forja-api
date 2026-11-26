package br.com.forja.domain.cliente;

import jakarta.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import br.com.forja.shared.search.TextoBusca;

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

                Predicate nome = TextoBusca.contemSemAcentos(
                        cb,
                        root.get("nome"),
                        termo);

                Predicate cpf = cb.like(
                        root.get("cpf"),
                        termo.trim());

                Predicate email = TextoBusca.contemSemAcentos(
                        cb,
                        root.get("email"),
                        termo);

                Predicate cr = TextoBusca.contemSemAcentos(
                        cb,
                        root.get("cr"),
                        termo);

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