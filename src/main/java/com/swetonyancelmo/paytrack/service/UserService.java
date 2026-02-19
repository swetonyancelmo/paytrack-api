package com.swetonyancelmo.paytrack.service;

import com.swetonyancelmo.paytrack.dtos.request.CreateUserDto;
import com.swetonyancelmo.paytrack.dtos.response.UserDto;
import com.swetonyancelmo.paytrack.exceptions.ExistingEmailException;
import com.swetonyancelmo.paytrack.exceptions.ResourceNotFoundException;
import com.swetonyancelmo.paytrack.mapper.UserMapper;
import com.swetonyancelmo.paytrack.model.User;
import com.swetonyancelmo.paytrack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.logging.Logger;

@Service
public class UserService {

    private Logger logger = Logger.getLogger(UserService.class.getName());

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserMapper userMapper;

    @Transactional
    public UserDto create(CreateUserDto dto) {
        logger.info("Criando um usuário");

        if(repository.existsByEmail(dto.email())) {
            logger.info("O email informado já está cadastrado");
            throw new ExistingEmailException("E-mail já cadastrado, tente novamente.");
        }

        User user = userMapper.toEntity(dto);

        User userSaved = repository.save(user);
        return userMapper.toDto(userSaved);
    }

    @Transactional(readOnly = true)
    public UserDto findById(Long id) {
        logger.info("Procurando um usuário por ID");
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o ID: " + id));

        return userMapper.toDto(user);
    }
}
