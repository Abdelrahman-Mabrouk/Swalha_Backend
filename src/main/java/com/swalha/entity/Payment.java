package com.swalha.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

import java.math.BigDecimal;

@Entity
@Table(name = "payments")
public class Payment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    @JsonBackReference
    private Student student;
    
    @NotNull
    @Min(1)
    @Max(12)
    @Column(nullable = false)
    private Integer month;
    
    @NotNull
    @Column(nullable = false)
    private Integer year;

    @NotNull
    @Column(nullable = false)
    private Integer day;
    
    @NotNull
    @Column(name = "amount_paid", nullable = false, precision = 10, scale = 2)
    private BigDecimal amountPaid;
    
    @NotNull
    @Column(name = "expected_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal expectedAmount;
    
    @NotNull
    @Column(name = "payment_status", nullable = false)
    private Boolean paymentStatus; // true for PAID, false for UNPAID
    private Boolean notApplicable = false;
    // Constructors
    public Payment() {}
    
    public Payment(Student student,Integer day, Integer month, Integer year, BigDecimal amountPaid, BigDecimal expectedAmount, Boolean paymentStatus) {
        this.student = student;
        this.day = day;
        this.month = month;
        this.year = year;
        this.amountPaid = amountPaid;
        this.expectedAmount = expectedAmount;
        this.paymentStatus = paymentStatus;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Student getStudent() {
        return student;
    }
    
    public void setStudent(Student student) {
        this.student = student;
    }
    
    public Integer getMonth() {
        return month;
    }
    
    public void setMonth(Integer month) {
        this.month = month;
    }
    
    public Integer getYear() {
        return year;
    }
    
    public void setYear(Integer year) {
        this.year = year;
    }
    
    public BigDecimal getAmountPaid() {
        return amountPaid;
    }
    
    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }
    
    public BigDecimal getExpectedAmount() {
        return expectedAmount;
    }
    
    public void setExpectedAmount(BigDecimal expectedAmount) {
        this.expectedAmount = expectedAmount;
    }
    
    public Boolean getPaymentStatus() {
        return paymentStatus;
    }
    
    public void setPaymentStatus(Boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    
    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", student=" + (student != null ? student.getId() : "null") +
                ", month=" + month +
                ", year=" + year +
                ", amountPaid=" + amountPaid +
                ", expectedAmount=" + expectedAmount +
                ", paymentStatus=" + paymentStatus +
                '}';
    }

    public void setNotApplicable(boolean b) {
        this.notApplicable=b;
    }
    public Integer getday(){
        return day;
    }
    public void setday(Integer day){
        this.day=day;
    }

}
