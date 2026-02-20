package com.swetonyancelmo.paytrack.controller;

import com.swetonyancelmo.paytrack.controller.docs.ExpenseControllerDocs;
import com.swetonyancelmo.paytrack.dtos.request.CreateExpenseDto;
import com.swetonyancelmo.paytrack.dtos.response.ExpenseDto;
import com.swetonyancelmo.paytrack.dtos.response.IncomeDto;
import com.swetonyancelmo.paytrack.service.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expenses/v1")
@Tag(name = "Expense", description = "Endpoints for Managing Expense")
public class ExpenseController implements ExpenseControllerDocs {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Override
    public ResponseEntity<ExpenseDto> create(@RequestBody @Valid CreateExpenseDto dto) {
        ExpenseDto expenseDto = expenseService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(expenseDto);
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Override
    public ResponseEntity<ExpenseDto> getByUserId(@RequestParam(name = "userId") Long userId) {
        return ResponseEntity.ok(expenseService.findByUserId(userId));
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id) {
        expenseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
