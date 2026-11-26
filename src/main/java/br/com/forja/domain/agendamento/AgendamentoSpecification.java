package br.com.forja.domain.agendamento;

import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import br.com.forja.shared.search.TextoBusca;

public class AgendamentoSpecification {

        private AgendamentoSpecification() {
        }

        public static Specification<Agendamento> filtrar(String termo) {
                return (root, query, cb) -> {
                        if (termo == null || termo.isBlank()) {
                                return cb.conjunction();
                        }

                        Predicate cliente = TextoBusca.contemSemAcentos(
                                        cb,
                                        root.join("cliente", JoinType.LEFT).get("nome"),
                                        termo);
                        Predicate raia = TextoBusca.contemSemAcentos(
                                        cb,
                                        root.join("raia", JoinType.LEFT).get("descricao"),
                                        termo);
                        Predicate status = TextoBusca.contemSemAcentos(
                                        cb,
                                        root.get("status"),
                                        termo);

                        return cb.or(cliente, raia, status);
                };
        }
}
