package br.com.forja.exception;

import java.util.List;

public record ValidationErrorResponse(
        Integer status,
        List<String> erros
) {
}