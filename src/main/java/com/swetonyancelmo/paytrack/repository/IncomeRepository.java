package com.swetonyancelmo.paytrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swetonyancelmo.paytrack.model.Income;

public interface IncomeRepository extends JpaRepository<Income, Long> {
    
    Income findByUserId(Long userId);

}
