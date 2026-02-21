package com.swetonyancelmo.paytrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swetonyancelmo.paytrack.model.Income;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface IncomeRepository extends JpaRepository<Income, Long> {
    
    Income findByUserId(Long userId);

    @Query("SELECT COALESCE(SUM(i.valor), 0) FROM Income i WHERE i.user.id = :userId")
    BigDecimal sumValorByUserId(@Param("userId") Long userId);

}
