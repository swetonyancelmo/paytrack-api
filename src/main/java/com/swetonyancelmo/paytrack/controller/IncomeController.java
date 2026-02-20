package com.swetonyancelmo.paytrack.controller;

import com.swetonyancelmo.paytrack.controller.docs.IncomeControllerDocs;
import com.swetonyancelmo.paytrack.dtos.request.CreateIncomeDto;
import com.swetonyancelmo.paytrack.dtos.response.IncomeDto;
import com.swetonyancelmo.paytrack.service.IncomeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/incomes/v1")
@Tag(name = "Income", description = "Endpoints for Managing Income")
public class IncomeController implements IncomeControllerDocs {

    private final IncomeService incomeService;

    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Override
    public ResponseEntity<IncomeDto> create(@RequestBody @Valid CreateIncomeDto dto) {
        IncomeDto income = incomeService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(income);
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Override
    public ResponseEntity<IncomeDto> getByUserId(@RequestParam(name = "userId") Long userId) {
        return ResponseEntity.ok(incomeService.findByUserId(userId));
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id) {
        incomeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
