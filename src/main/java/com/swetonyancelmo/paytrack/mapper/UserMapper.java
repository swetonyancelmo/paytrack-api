package com.swetonyancelmo.paytrack.mapper;

import com.swetonyancelmo.paytrack.dtos.request.CreateUserDto;
import com.swetonyancelmo.paytrack.dtos.response.UserDto;
import com.swetonyancelmo.paytrack.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    public static final UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    public abstract User toEntity(CreateUserDto createUserDto);

    public abstract User toUser(UserDto userDto);

    public abstract UserDto toDto(User user);

}
