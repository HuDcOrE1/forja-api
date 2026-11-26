package br.com.forja.domain.raia;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import br.com.forja.shared.search.TextoBusca;

public class RaiaSpecification {

        private RaiaSpecification() {
        }

        public static Specification<Raia> filtrar(String termo) {
                return (root, query, cb) -> {
                        Predicate ativa = cb.isTrue(root.get("ativa"));

                        if (termo == null || termo.isBlank()) {
                                return ativa;
                        }

                        Predicate numero = cb.like(
                                        root.get("numero").as(String.class),
                                        "%" + TextoBusca.normalizar(termo.trim()) + "%");
                        Predicate descricao = TextoBusca.contemSemAcentos(
                                        cb,
                                        root.get("descricao"),
                                        termo);

                        return cb.and(ativa, cb.or(numero, descricao));
                };
        }
}
