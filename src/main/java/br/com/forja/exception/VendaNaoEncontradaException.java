package br.com.forja.exception;

public class VendaNaoEncontradaException
                extends RuntimeException {

        public VendaNaoEncontradaException(
                        Long id) {

                super(
                                "Venda com id "
                                                + id
                                                + " não encontrada.");
        }
}