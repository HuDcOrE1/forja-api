package br.com.forja.exception;

public class RaiaNaoEncontradaException
                extends RuntimeException {

        public RaiaNaoEncontradaException(
                        Long id) {

                super(
                                "Raia com id "
                                                + id
                                                + " não encontrada.");
        }
}