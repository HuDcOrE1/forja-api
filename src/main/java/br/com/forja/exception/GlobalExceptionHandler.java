package br.com.forja.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
            ClienteNaoEncontradoException.class
    )
    public ResponseEntity<ErrorResponse>
    tratarClienteNaoEncontrado(
            ClienteNaoEncontradoException ex
    ) {

        ErrorResponse response =
                new ErrorResponse(
                        HttpStatus.NOT_FOUND.value(),
                        ex.getMessage(),
                        LocalDateTime.now()
                );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(
            ProdutoNaoEncontradoException.class
    )
    public ResponseEntity<ErrorResponse>
    tratarProdutoNaoEncontrado(
            ProdutoNaoEncontradoException ex
    ) {

        ErrorResponse response =
                new ErrorResponse(
                        HttpStatus.NOT_FOUND.value(),
                        ex.getMessage(),
                        LocalDateTime.now()
                );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(
            RaiaNaoEncontradaException.class
    )
    public ResponseEntity<ErrorResponse>
    tratarRaiaNaoEncontrada(
            RaiaNaoEncontradaException ex
    ) {

        ErrorResponse response =
                new ErrorResponse(
                        HttpStatus.NOT_FOUND.value(),
                        ex.getMessage(),
                        LocalDateTime.now()
                );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(
            AgendamentoNaoEncontradoException.class
    )
    public ResponseEntity<ErrorResponse>
    tratarAgendamentoNaoEncontrado(
            AgendamentoNaoEncontradoException ex
    ) {

        ErrorResponse response =
                new ErrorResponse(
                        HttpStatus.NOT_FOUND.value(),
                        ex.getMessage(),
                        LocalDateTime.now()
                );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(
            ConflitoAgendamentoException.class
    )
    public ResponseEntity<ErrorResponse>
    tratarConflitoAgendamento(
            ConflitoAgendamentoException ex
    ) {

        ErrorResponse response =
                new ErrorResponse(
                        HttpStatus.CONFLICT.value(),
                        ex.getMessage(),
                        LocalDateTime.now()
                );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }

    @ExceptionHandler(
            MethodArgumentNotValidException.class
    )
    public ResponseEntity<ValidationErrorResponse>
    tratarValidacao(
            MethodArgumentNotValidException ex
    ) {

        List<String> erros =
                ex.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(error -> error.getDefaultMessage())
                        .toList();

        ValidationErrorResponse response =
                new ValidationErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        erros
                );

        return ResponseEntity
                .badRequest()
                .body(response);
    }

    @ExceptionHandler(
            DataIntegrityViolationException.class
    )
    public ResponseEntity<ErrorResponse>
    tratarIntegridade(
            DataIntegrityViolationException ex
    ) {

        ErrorResponse response =
                new ErrorResponse(
                        HttpStatus.CONFLICT.value(),
                        ex.getMostSpecificCause().getMessage(),
                        LocalDateTime.now()
                );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }

    @ExceptionHandler(
            Exception.class
    )
    public ResponseEntity<ErrorResponse>
    tratarErroGenerico(
            Exception ex
    ) {

        ErrorResponse response =
                new ErrorResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        ex.getMessage(),
                        LocalDateTime.now()
                );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

        @ExceptionHandler(
        VendaNaoEncontradaException.class
        )
        public ResponseEntity<ErrorResponse>
        tratarVendaNaoEncontrada(
                VendaNaoEncontradaException ex
        ) {

            ErrorResponse response =
                    new ErrorResponse(
                            HttpStatus.NOT_FOUND.value(),
                            ex.getMessage(),
                            LocalDateTime.now()
                    );

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(response);
        }

        @ExceptionHandler(
        IllegalArgumentException.class
        )
        public ResponseEntity<ErrorResponse>
        tratarIllegalArgument(
                IllegalArgumentException ex
        ) {

            ErrorResponse response =
                    new ErrorResponse(
                            HttpStatus.BAD_REQUEST.value(),
                            ex.getMessage(),
                            LocalDateTime.now()
                    );

            return ResponseEntity
                    .badRequest()
                    .body(response);
        }
}