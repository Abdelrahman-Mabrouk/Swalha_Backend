package com.swalha.dto;

import com.swalha.entity.Payment;
import com.swalha.entity.Student;

import java.math.BigDecimal;

public class PaymentResponse {
    
    private Long id;
    private Long studentId;
    private String studentName;
    private Integer day;
    private Integer month;
    private Integer year;
    private BigDecimal amountPaid;
    private BigDecimal expectedAmount;
    private Boolean paymentStatus;
    private String statusText;

    
    // Constructors
    public PaymentResponse() {}
    
    public PaymentResponse(Payment payment) {
        this.id = payment.getId();
        this.studentId = payment.getStudent().getId();
        // Safely get student name, handling potential null values
        String studentName = "Unknown Student";
        if (payment.getStudent() != null && payment.getStudent().getName() != null) {
            studentName = payment.getStudent().getName().trim();
            if (studentName.isEmpty()) {
                studentName = "Unknown Student";
            }
        }
        this.studentName = studentName;
        this.day = payment.getday();
        this.month = payment.getMonth();
        this.year = payment.getYear();
        this.amountPaid = payment.getAmountPaid();
        this.expectedAmount = payment.getExpectedAmount();
        this.paymentStatus = payment.getPaymentStatus();
        this.statusText = payment.getPaymentStatus() ? "PAID" : "UNPAID";
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getStudentId() {
        return studentId;
    }
    
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
    
    public String getStudentName() {
        return studentName;
    }
    
    public void setStudentName(String studentName) {
        this.studentName = studentName;
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
    
    public String getStatusText() {
        return statusText;
    }
    
    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }
    public void setday(Integer day) {
        this.day = day;
    }
    public Integer getday() {
        return day;
    }
    @Override
    public String toString() {
        return "PaymentResponse{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                ", month=" + month +
                ", year=" + year +
                ", amountPaid=" + amountPaid +
                ", expectedAmount=" + expectedAmount +
                ", paymentStatus=" + paymentStatus +
                ", statusText='" + statusText + '\'' +
                '}';
    }
}
