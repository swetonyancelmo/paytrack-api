package com.swetonyancelmo.paytrack.dtos.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateIncomeDto(
        @NotBlank(message = "A descrição da renda é obrigatória")
        @Size(min = 3, max = 80, message = "A descrição deve conter entre 3 e 80 caracteres")
        String descricao,

        @NotNull(message = "O valor é obrigatório")
        @Positive(message = "O valor não pode ser 0 ou menor que 0")
        BigDecimal valor,

        @NotNull(message = "A data é obrigatória")
        @PastOrPresent(message = "A data da renda não pode ser futura")
        LocalDate data,

        @NotNull(message = "É obrigatório informar o usuário")
        @Positive
        Long id
) {
}
