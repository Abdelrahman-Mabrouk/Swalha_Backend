package com.swalha.service;

import com.swalha.dto.MonthlyPaymentDto;
import com.swalha.entity.MonthlyPayment;
import com.swalha.entity.Payment;
import com.swalha.entity.Student;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentService {
    
    Payment addPayment(Long studentId, Integer day,Integer month, Integer year, BigDecimal amountPaid, BigDecimal expectedAmount);
    
    List<Payment> getAllPaymentsByStudent(Long studentId);
    
    List<Payment> getPaymentsByStudentAndYear(Long studentId, Integer year);
    
    List<Payment> getUnpaidPaymentsByStudent(Long studentId);
    
    BigDecimal getOutstandingBalance(Long studentId);
    
    Integer getUnpaidMonthsCount(Long studentId);

    List<MonthlyPaymentDto> getMonthlyPaymentsByStudent(Long studentId);

    MonthlyPayment getMonthlyPaymentByStudentAndMonth(Long studentId, Integer year, Integer month);
    List<Integer> getStudentMonths(Student student) ;
    List<Integer> getStudentYears(Student student);

    }
