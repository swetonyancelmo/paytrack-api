package com.swetonyancelmo.paytrack.dtos.response;

import java.math.BigDecimal;

public record SummaryDto(
        BigDecimal totalRendas,
        BigDecimal totalDespesas,
        BigDecimal saldoFinal
) {
}
