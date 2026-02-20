package com.swetonyancelmo.paytrack.repository;

import com.swetonyancelmo.paytrack.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Expense findByUserId(Long id);

}
