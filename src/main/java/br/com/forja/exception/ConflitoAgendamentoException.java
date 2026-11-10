package br.com.forja.exception;

public class ConflitoAgendamentoException
        extends RuntimeException {

    public ConflitoAgendamentoException() {

        super(
                "Já existe agendamento para esta raia no período informado.");
    }

    public ConflitoAgendamentoException(
            String mensagem) {

        super(mensagem);
    }
}