package com.swalha.controller;

import com.swalha.dto.MonthlyPaymentDto;
import com.swalha.dto.PaymentHistoryDTO;
import com.swalha.entity.MonthlyPayment;
import com.swalha.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/monthly-payments")
@CrossOrigin(origins = "*")
public class MonthlyPaymentController {

    @Autowired
    private PaymentService monthlyPaymentService;

    // عرض كل الأشهر لطالب معين
    @GetMapping("/{studentId}")
    public List<MonthlyPaymentDto> getMonthlyPayments(@PathVariable Long studentId) {
        return monthlyPaymentService.getMonthlyPaymentsByStudent(studentId);
    }

    // عرض ملخص شهر معين
    @GetMapping("/{studentId}/{year}/{month}")
    public MonthlyPayment getMonthlyPayment(
            @PathVariable Long studentId,
            @PathVariable Integer year,
            @PathVariable Integer month) {

        return monthlyPaymentService.getMonthlyPaymentByStudentAndMonth(studentId, year, month);
    }
}
