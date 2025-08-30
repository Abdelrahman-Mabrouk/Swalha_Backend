package com.swalha.repository;

import com.swalha.entity.Payment;
import com.swalha.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    
    List<Payment> findByStudent(Student student);

    
    List<Payment> findByStudentAndYear(Student student, Integer year);

    List<Payment> findByStudentAndMonth(Student student,Integer month);
    
    List<Payment> findByStudentAndYearAndMonth(Student student, Integer year, Integer month);
    
    @Query("SELECT p FROM Payment p WHERE p.student = :student AND p.paymentStatus = false")
    List<Payment> findUnpaidPaymentsByStudent(@Param("student") Student student);
    
    @Query("SELECT p FROM Payment p WHERE p.student = :student AND p.year = :year AND p.paymentStatus = false")
    List<Payment> findUnpaidPaymentsByStudentAndYear(@Param("student") Student student, @Param("year") Integer year);
    
    List<Payment> findByPaymentStatus(Boolean paymentStatus);
}
