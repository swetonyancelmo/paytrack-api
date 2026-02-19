package com.swetonyancelmo.paytrack.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.swetonyancelmo.paytrack.dtos.request.CreateIncomeDto;
import com.swetonyancelmo.paytrack.dtos.response.IncomeDto;
import com.swetonyancelmo.paytrack.model.Income;

@Mapper(componentModel = "spring")
public abstract class IncomeMapper {
    
    private static final IncomeMapper INSTANCE = Mappers.getMapper(IncomeMapper.class);
    
    public abstract Income toEntity(CreateIncomeDto createIncomeDto);

    public abstract Income toEntity(IncomeDto incomeDto);

    public abstract IncomeDto toDto(Income income);

}
