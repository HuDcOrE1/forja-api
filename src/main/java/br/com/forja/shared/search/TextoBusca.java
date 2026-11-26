package br.com.forja.shared.search;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;

import java.text.Normalizer;
import java.util.Locale;

public final class TextoBusca {

    private static final String CARACTERES_COM_ACENTO = "áàâãäéèêëíìîïóòôõöúùûüçñ";
    private static final String CARACTERES_SEM_ACENTO = "aaaaaeeeeiiiiooooouuuucn";

    private TextoBusca() {
    }

    public static Predicate contemSemAcentos(
            CriteriaBuilder cb,
            Expression<String> campo,
            String termo) {

        Expression<String> campoNormalizado = cb.function(
                "translate",
                String.class,
                cb.lower(campo),
                cb.literal(CARACTERES_COM_ACENTO),
                cb.literal(CARACTERES_SEM_ACENTO));

        return cb.like(
                campoNormalizado,
                "%" + normalizar(termo.trim()) + "%");
    }

    public static String normalizar(String texto) {
        return Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("\\p{M}+", "")
                .toLowerCase(Locale.ROOT);
    }
}
