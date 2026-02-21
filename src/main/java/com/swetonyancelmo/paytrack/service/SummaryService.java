package com.swetonyancelmo.paytrack.service;

import com.swetonyancelmo.paytrack.dtos.response.SummaryDto;
import com.swetonyancelmo.paytrack.exceptions.ResourceNotFoundException;
import com.swetonyancelmo.paytrack.model.User;
import com.swetonyancelmo.paytrack.repository.ExpenseRepository;
import com.swetonyancelmo.paytrack.repository.IncomeRepository;
import com.swetonyancelmo.paytrack.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class SummaryService {

    private final IncomeRepository incomeRepository;

    private final ExpenseRepository expenseRepository;

    private final UserRepository userRepository;

    public SummaryService(IncomeRepository incomeRepository, ExpenseRepository expenseRepository, UserRepository userRepository) {
        this.incomeRepository = incomeRepository;
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public SummaryDto summary(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o ID: " + userId));

        BigDecimal totalIncome = incomeRepository.sumValorByUserId(user.getId());
        BigDecimal totalExpense = expenseRepository.sumValorByUserId(user.getId());
        BigDecimal balance = totalIncome.subtract(totalExpense);

        return new SummaryDto(
                totalIncome,
                totalExpense,
                balance
        );
    }

}
