package br.com.forja.domain.produto;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import br.com.forja.shared.search.TextoBusca;

public class CategoriaProdutoSpecification {

    private CategoriaProdutoSpecification() {
    }

    public static Specification<CategoriaProduto> filtrar(String termo) {
        return (root, query, cb) -> {
            Predicate ativo = cb.isTrue(root.get("ativo"));

            if (termo == null || termo.isBlank()) {
                return ativo;
            }

            Predicate nome = TextoBusca.contemSemAcentos(cb, root.get("nome"), termo);
            Predicate descricao = TextoBusca.contemSemAcentos(cb, root.get("descricao"), termo);

            return cb.and(ativo, cb.or(nome, descricao));
        };
    }
}
