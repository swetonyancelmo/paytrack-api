package com.swetonyancelmo.paytrack.repository;

import com.swetonyancelmo.paytrack.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Expense findByUserId(Long userId);

    @Query("SELECT COALESCE(SUM(e.valor), 0) FROM Expense e WHERE e.user.id = :userId")
    BigDecimal sumValorByUserId(@Param("userId") Long userId);

}
