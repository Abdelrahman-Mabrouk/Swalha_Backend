package com.swalha.repository;

import com.swalha.entity.PaymentHistory;
import com.swalha.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, Long> {
    List<PaymentHistory> findByStudentAndYearAndMonth(Student student, Integer year, Integer month);
}


