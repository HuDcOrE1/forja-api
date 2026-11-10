package br.com.forja.domain.dashboard.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record FaturamentoDiarioResponse(

                LocalDate data,

                BigDecimal valor

) {
}