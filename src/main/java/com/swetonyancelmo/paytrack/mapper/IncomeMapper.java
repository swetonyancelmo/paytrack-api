package com.swetonyancelmo.paytrack.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.swetonyancelmo.paytrack.dtos.request.CreateIncomeDto;
import com.swetonyancelmo.paytrack.dtos.response.IncomeDto;
import com.swetonyancelmo.paytrack.model.Income;

@Mapper(componentModel = "spring")
public abstract class IncomeMapper {
    
    private static final IncomeMapper INSTANCE = Mappers.getMapper(IncomeMapper.class);
    
    public abstract Income toEntity(CreateIncomeDto createIncomeDto);

    public abstract Income toEntity(IncomeDto incomeDto);

    @Mapping(source = "user.id", target = "userId")
    public abstract IncomeDto toDto(Income income);

}
