package com.swetonyancelmo.paytrack.dtos.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record IncomeDto(
        Long id,
        String descricao,
        BigDecimal valor,
        LocalDate data,
        Long userId
) {}
