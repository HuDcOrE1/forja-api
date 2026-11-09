package br.com.forja.exception;

import java.time.LocalDateTime;

public record ErrorResponse(

        Integer status,
        String erro,
        LocalDateTime dataHora

) {
}