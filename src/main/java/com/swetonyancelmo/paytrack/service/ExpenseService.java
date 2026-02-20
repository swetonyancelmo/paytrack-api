package com.swetonyancelmo.paytrack.service;

import com.swetonyancelmo.paytrack.dtos.request.CreateExpenseDto;
import com.swetonyancelmo.paytrack.dtos.response.ExpenseDto;
import com.swetonyancelmo.paytrack.exceptions.ResourceNotFoundException;
import com.swetonyancelmo.paytrack.mapper.ExpenseMapper;
import com.swetonyancelmo.paytrack.model.Expense;
import com.swetonyancelmo.paytrack.model.User;
import com.swetonyancelmo.paytrack.repository.ExpenseRepository;
import com.swetonyancelmo.paytrack.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.logging.Logger;

@Service
public class ExpenseService {

    private Logger logger = Logger.getLogger(UserService.class.getName());

    private final ExpenseRepository expenseRepository;

    private final UserRepository userRepository;

    private final ExpenseMapper expenseMapper;

    public ExpenseService(ExpenseRepository expenseRepository, UserRepository userRepository, ExpenseMapper expenseMapper) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
        this.expenseMapper = expenseMapper;
    }

    @Transactional
    public ExpenseDto create(CreateExpenseDto dto) {
        logger.info("Fazendo a criação de uma nova despesa");
        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o ID: " + dto.userId()));

        Expense expense = new Expense();
        expense.setDescricao(dto.descricao());
        expense.setValor(dto.valor());
        expense.setData(dto.data());
        expense.setTipo(dto.tipo());
        expense.setUser(user);

        Expense expenseSaved = expenseRepository.save(expense);
        return expenseMapper.toDto(expenseSaved);
    }

    @Transactional(readOnly = true)
    public ExpenseDto findByUserId(Long userId) {
        logger.info("Buscando uma despesa pelo ID do usuário");
        boolean userExists = userRepository.existsById(userId);

        if(!userExists) {
            throw new ResourceNotFoundException("Usuário não encontrado com ID: " + userId);
        }

        Expense expense = expenseRepository.findByUserId(userId);
        return expenseMapper.toDto(expense);
    }

    @Transactional
    public void delete(Long id) {
        logger.info("Deletando uma despesa pelo ID");
        Expense expense = expenseRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Despesa não encontrada com o ID: " + id));
        expenseRepository.delete(expense);
    }
}
