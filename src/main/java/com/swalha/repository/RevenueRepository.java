package com.swalha.repository;

import com.swalha.entity.Revenue;
import com.swalha.entity.RevenueCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface RevenueRepository extends JpaRepository<Revenue, Long> {
    
    List<Revenue> findByCategory(RevenueCategory category);
    
    List<Revenue> findByMonth(String month);
    
    List<Revenue> findByCategoryAndMonth(RevenueCategory category, String month);
    
    @Query("SELECT r FROM Revenue r WHERE r.name LIKE %:name%")
    List<Revenue> findByNameContaining(@Param("name") String name);
    
    @Query("SELECT SUM(r.amount) FROM Revenue r")
    BigDecimal getTotalRevenue();
    
    @Query("SELECT SUM(r.amount) FROM Revenue r WHERE r.month = :month")
    BigDecimal getTotalRevenueByMonth(@Param("month") String month);
    
    @Query("SELECT SUM(r.amount) FROM Revenue r WHERE r.category = :category")
    BigDecimal getTotalRevenueByCategory(@Param("category") RevenueCategory category);
} 