package com.swetonyancelmo.paytrack.dtos.response;

import com.swetonyancelmo.paytrack.model.enums.ExpenseType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExpenseDto(
   Long id,
   String descricao,
   BigDecimal valor,
   LocalDate data,
   ExpenseType tipo,
   Long userId
) {}
