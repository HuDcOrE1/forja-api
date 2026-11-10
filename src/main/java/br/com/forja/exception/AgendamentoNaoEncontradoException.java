package br.com.forja.exception;

public class AgendamentoNaoEncontradoException
                extends RuntimeException {

        public AgendamentoNaoEncontradoException(
                        Long id) {

                super(
                                "Agendamento com id "
                                                + id
                                                + " não encontrado.");
        }
}