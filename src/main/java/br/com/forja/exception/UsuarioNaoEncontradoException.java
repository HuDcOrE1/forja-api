package br.com.forja.exception;

public class UsuarioNaoEncontradoException
        extends RuntimeException {

    public UsuarioNaoEncontradoException(
            Long id) {

        super(
                "Usuário não encontrado. ID: "
                        + id);

    }

}
