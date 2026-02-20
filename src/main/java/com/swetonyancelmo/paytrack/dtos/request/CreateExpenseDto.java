package com.swetonyancelmo.paytrack.dtos.request;

import com.swetonyancelmo.paytrack.model.enums.ExpenseType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateExpenseDto(
        @NotBlank(message = "A descrição da despesa é obrigatória")
        @Size(min = 3, max = 80, message = "A descrição deve conter entre 3 e 80 caracteres")
        String descricao,

        @NotNull(message = "O valor é obrigatório")
        @Positive(message = "O valor não pode ser 0 ou menor que 0")
        BigDecimal valor,

        @NotNull(message = "A data é obrigatória")
        @FutureOrPresent(message = "A data da despesa planejada deve ser hoje ou futura")
        LocalDate data,

        @NotNull(message = "O tipo de despesa é obrigatório")
        ExpenseType tipo,

        @NotNull(message = "É obrigatório informar o usuário")
        @Positive
        Long userId
) {}
