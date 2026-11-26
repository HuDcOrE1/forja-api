package br.com.forja.shared.search;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TextoBuscaTest {

    @Test
    void normalizaAcentosEMaiusculas() {
        assertEquals("joao", TextoBusca.normalizar("João"));
        assertEquals("andre", TextoBusca.normalizar("ANDRÉ"));
        assertEquals("arvore", TextoBusca.normalizar("Árvore"));
        assertEquals("luis", TextoBusca.normalizar("LUÍS"));
    }
}
