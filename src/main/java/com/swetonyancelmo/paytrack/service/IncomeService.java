package com.swetonyancelmo.paytrack.service;

import java.util.logging.Logger;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swetonyancelmo.paytrack.dtos.request.CreateIncomeDto;
import com.swetonyancelmo.paytrack.dtos.response.IncomeDto;
import com.swetonyancelmo.paytrack.exceptions.ResourceNotFoundException;
import com.swetonyancelmo.paytrack.mapper.IncomeMapper;
import com.swetonyancelmo.paytrack.model.Income;
import com.swetonyancelmo.paytrack.model.User;
import com.swetonyancelmo.paytrack.repository.IncomeRepository;
import com.swetonyancelmo.paytrack.repository.UserRepository;

@Slf4j
@Service
public class IncomeService {
    
    private Logger logger = Logger.getLogger(UserService.class.getName());

    private final IncomeRepository incomeRepository;

    private final UserRepository userRepository;

    private final IncomeMapper incomeMapper;

    public IncomeService(IncomeRepository incomeRepository, IncomeMapper incomeMapper, UserRepository userRepository) {
        this.incomeRepository = incomeRepository;
        this.incomeMapper = incomeMapper;
        this.userRepository = userRepository;
    }

    @Transactional
    public IncomeDto create(CreateIncomeDto dto) {
        logger.info("Criando uma nova renda");
        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o ID: " + dto.userId()));

        Income income = new Income();
        income.setDescricao(dto.descricao());
        income.setValor(dto.valor());
        income.setData(dto.data());
        income.setUser(user);

        Income incomeSaved = incomeRepository.save(income);
        return incomeMapper.toDto(incomeSaved);
    }

    @Transactional(readOnly = true)
    public IncomeDto findByUserId(Long userId) {
        logger.info("Realizando a busca de renda pelo ID do usuário");
        boolean userExists = userRepository.existsById(userId);
        
        if(!userExists) {
            throw new ResourceNotFoundException("Usuário não encontrado com o ID: " + userId);
        }

        Income income = incomeRepository.findByUserId(userId);
        return incomeMapper.toDto(income);
    }

    @Transactional
    public void delete(Long id){
        logger.info("Deletando uma renda pelo ID");
        Income income = incomeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Renda não encontrada"));
        incomeRepository.delete(income);
    }
}
