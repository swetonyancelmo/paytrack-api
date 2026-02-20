package com.swetonyancelmo.paytrack.mapper;

import com.swetonyancelmo.paytrack.dtos.request.CreateExpenseDto;
import com.swetonyancelmo.paytrack.dtos.response.ExpenseDto;
import com.swetonyancelmo.paytrack.model.Expense;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class ExpenseMapper {

    private static final ExpenseMapper INSTANCE = Mappers.getMapper(ExpenseMapper.class);

    public abstract Expense toEntity(CreateExpenseDto createExpenseDto);

    public abstract Expense toEntity(ExpenseDto expenseDto);

    @Mapping(source = "user.id", target = "userId")
    public abstract ExpenseDto toDto(Expense expense);

}
