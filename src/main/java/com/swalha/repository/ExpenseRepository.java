package com.swalha.repository;

import com.swalha.entity.Expense;
import com.swalha.entity.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    
    List<Expense> findByCategory(ExpenseCategory category);
    
    @Query("SELECT e FROM Expense e WHERE e.name LIKE %:name%")
    List<Expense> findByNameContaining(@Param("name") String name);
    
    @Query("SELECT SUM(e.amount) FROM Expense e")
    BigDecimal getTotalExpense();
    
    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.category = :category")
    BigDecimal getTotalExpenseByCategory(@Param("category") ExpenseCategory category);
} 