package com.swetonyancelmo.paytrack.mapper;

import com.swetonyancelmo.paytrack.dtos.request.CreateUserDto;
import com.swetonyancelmo.paytrack.dtos.response.UserDto;
import com.swetonyancelmo.paytrack.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static User toEntity(CreateUserDto dto) {
        return new User(
                dto.nome(),
                dto.email(),
                dto.senha()
        );
    }

    public static UserDto toDto(User entity) {
        return new UserDto(
                entity.getId(),
                entity.getNome(),
                entity.getEmail()
        );
    }

}
