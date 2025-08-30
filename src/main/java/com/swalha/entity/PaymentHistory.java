package com.swalha.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_history")
public class PaymentHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    private Integer month;
    private Integer year;
    private BigDecimal amountPaid;
    private BigDecimal expectedAmount;

    private LocalDateTime paymentDate;

    public PaymentHistory() {}

    public PaymentHistory(Student student, Integer month, Integer year, BigDecimal amountPaid, BigDecimal expectedAmount, LocalDateTime paymentDate) {
        this.student = student;
        this.month = month;
        this.year = year;
        this.amountPaid = amountPaid;
        this.expectedAmount = expectedAmount;
        this.paymentDate = paymentDate;
    }

    public Long getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public Integer getMonth() {
        return month;
    }

    public BigDecimal getExpectedAmount() {
        return expectedAmount;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public Integer getYear() {
        return year;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public void setExpectedAmount(BigDecimal expectedAmount) {
        this.expectedAmount = expectedAmount;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }
}

