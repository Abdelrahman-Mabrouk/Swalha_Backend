package com.swalha.repository;

import com.swalha.dto.MonthlyPaymentDto;
import com.swalha.entity.MonthlyPayment;
import com.swalha.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MonthlyPaymentRepository extends JpaRepository<MonthlyPayment, Long> {
    Optional<MonthlyPayment> findByStudentAndYearAndMonth(Student student, Integer year, Integer month);

    List<MonthlyPayment> findByStudentId(Long studentId);

    Optional<Object> findByStudentIdAndYearAndMonth(Long studentId, Integer year, Integer month);
}