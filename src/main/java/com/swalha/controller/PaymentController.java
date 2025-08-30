package com.swalha.controller;

import com.swalha.dto.PaymentRequest;
import com.swalha.dto.PaymentResponse;
import com.swalha.entity.MonthlyPayment;
import com.swalha.entity.Payment;
import com.swalha.service.PaymentService;
import com.swalha.service.SystemSettingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PaymentController {
    
    @Autowired
    private PaymentService paymentService;
    
    @Autowired
    private SystemSettingService systemSettingService;
    
    /**
     * Add a payment for a student
     * POST /api/payments
     */
    @PostMapping
    public ResponseEntity<?> addPayment(@Valid @RequestBody PaymentRequest paymentRequest ) {
        try {
            System.out.println("Received payment request: " + paymentRequest);
            
            // Calculate expected amount based on student status and current monthly fee
            BigDecimal expectedAmount = calculateExpectedAmount(paymentRequest.getStudentId(), paymentRequest.getMonth(), paymentRequest.getYear());

            Payment payment = paymentService.addPayment(
                paymentRequest.getStudentId(),
                paymentRequest.getday(),
                paymentRequest.getMonth(),
                paymentRequest.getYear(),
                paymentRequest.getAmountPaid(),
                expectedAmount
            );
            
            System.out.println("Payment created successfully: " + payment.getId());
            
            // Create a simple response without trying to access the student entity
            // to avoid lazy loading issues
            PaymentResponse response = new PaymentResponse();
            response.setId(payment.getId());
            response.setStudentId(payment.getStudent().getId());
            response.setMonth(payment.getMonth());
            response.setYear(payment.getYear());
            response.setAmountPaid(payment.getAmountPaid());
            response.setExpectedAmount(payment.getExpectedAmount());
            response.setPaymentStatus(payment.getPaymentStatus());
            response.setStatusText(payment.getPaymentStatus() ? "PAID" : "UNPAID");
            
            // Try to get student name safely
            try {
                String studentName = payment.getStudent().getName();
                response.setStudentName(studentName != null ? studentName : "Student ID: " + payment.getStudent().getId());
            } catch (Exception e) {
                System.out.println("Error getting student name: " + e.getMessage());
                response.setStudentName("Student ID: " + payment.getStudent().getId());
            }
            
            System.out.println("Payment response created: " + response);
            
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(true, "Payment added successfully", response));
                
        } catch (RuntimeException e) {
            System.out.println("Error in addPayment: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest()
                .body(new ApiResponse(false, e.getMessage(), null));
        } catch (Exception e) {
            System.out.println("Unexpected error in addPayment: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse(false, "Internal server error: " + e.getMessage(), null));
        }
    }
    
    /**
     * Get all payments for a specific student
     * GET /api/payments/student/{studentId}
     */
    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getPaymentsByStudent(@PathVariable Long studentId) {
        try {
            List<Payment> payments = paymentService.getAllPaymentsByStudent(studentId);
            List<PaymentResponse> responses = payments.stream()
                .map(PaymentResponse::new)
                .collect(Collectors.toList());
            
            return ResponseEntity.ok(new ApiResponse(true, "Payments retrieved successfully", responses));
            
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body(new ApiResponse(false, e.getMessage(), null));
        }
    }
    
    /**
     * Get payments for a specific student and year
     * GET /api/payments/student/{studentId}/year/{year}
     */
    @GetMapping("/student/{studentId}/year/{year}")
    public ResponseEntity<?> getPaymentsByStudentAndYear(
            @PathVariable Long studentId, 
            @PathVariable Integer year) {
        try {
            List<Payment> payments = paymentService.getPaymentsByStudentAndYear(studentId, year);
            List<PaymentResponse> responses = payments.stream()
                .map(PaymentResponse::new)
                .collect(Collectors.toList());
            
            return ResponseEntity.ok(new ApiResponse(true, "Payments retrieved successfully", responses));
            
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body(new ApiResponse(false, e.getMessage(), null));
        }
    }
    
    /**
     * Get unpaid payments for a specific student
     * GET /api/payments/student/{studentId}/unpaid
     */
    @GetMapping("/student/{studentId}/unpaid")
    public ResponseEntity<?> getUnpaidPaymentsByStudent(@PathVariable Long studentId) {
        try {
            List<Payment> payments = paymentService.getUnpaidPaymentsByStudent(studentId);
            List<PaymentResponse> responses = payments.stream()
                .map(PaymentResponse::new)
                .collect(Collectors.toList());
            
            return ResponseEntity.ok(new ApiResponse(true, "Unpaid payments retrieved successfully", responses));
            
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body(new ApiResponse(false, e.getMessage(), null));
        }
    }
    
    /**
     * Get outstanding balance for a student
     * GET /api/payments/student/{studentId}/balance
     */
    @GetMapping("/student/{studentId}/balance")
    public ResponseEntity<?> getOutstandingBalance(@PathVariable Long studentId) {
        try {
            BigDecimal balance = paymentService.getOutstandingBalance(studentId);
            
            return ResponseEntity.ok(new ApiResponse(true, "Outstanding balance retrieved successfully", balance));
            
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body(new ApiResponse(false, e.getMessage(), null));
        }
    }
    
    /**
     * Get unpaid months count for a student
     * GET /api/payments/student/{studentId}/unpaid-count
     */
    @GetMapping("/student/{studentId}/unpaid-count")
public ResponseEntity<?> getUnpaidMonthsCount(@PathVariable Long studentId) {
        try {
            Integer unpaidCount = paymentService.getUnpaidMonthsCount(studentId);
            
            return ResponseEntity.ok(new ApiResponse(true, "Unpaid months count retrieved successfully", unpaidCount));
            
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body(new ApiResponse(false, e.getMessage(), null));
        }
    }
    
    /**
     * Get payment summary for a student (balance + unpaid count)
     * GET /api/payments/student/{studentId}/summary
     */
    @GetMapping("/student/{studentId}/summary")
    public ResponseEntity<?> getPaymentSummary(@PathVariable Long studentId) {
        try {
            BigDecimal balance = paymentService.getOutstandingBalance(studentId);
            Integer unpaidCount = paymentService.getUnpaidMonthsCount(studentId);
//            BigDecimal balance = BigDecimal.valueOf(1);
//            Integer unpaidCount = 0;
            
            PaymentSummary summary = new PaymentSummary(studentId, balance, unpaidCount);
            
            return ResponseEntity.ok(new ApiResponse(true, "Payment summary retrieved successfully", summary));
            
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body(new ApiResponse(false, e.getMessage(), null));
        }
    }
    
    // Helper classes for API responses
    public static class ApiResponse {
        private boolean success;
        private String message;
        private Object data;
        
        public ApiResponse(boolean success, String message, Object data) {
            this.success = success;
            this.message = message;
            this.data = data;
        }
        
        // Getters and Setters
        public boolean isSuccess() {
            return success;
        }
        
        public void setSuccess(boolean success) {
            this.success = success;
        }
        
        public String getMessage() {
            return message;
        }
        
        public void setMessage(String message) {
            this.message = message;
        }
        
        public Object getData() {
            return data;
        }
        
        public void setData(Object data) {
            this.data = data;
        }
        
        @Override
        public String toString() {
            return "ApiResponse{" +
                    "success=" + success +
                    ", message='" + message + '\'' +
                    ", data=" + data +
                    '}';
        }
    }
    
    public static class PaymentSummary {
        private Long studentId;
        private BigDecimal outstandingBalance;
        private Integer unpaidMonthsCount;
        
        public PaymentSummary(Long studentId, BigDecimal outstandingBalance, Integer unpaidMonthsCount) {
            this.studentId = studentId;
            this.outstandingBalance = outstandingBalance;
            this.unpaidMonthsCount = unpaidMonthsCount;
        }
        
        // Getters and Setters
        public Long getStudentId() {
            return studentId;
        }
        
        public void setStudentId(Long studentId) {
            this.studentId = studentId;
        }
        
        public BigDecimal getOutstandingBalance() {
            return outstandingBalance;
        }
        
        public void setOutstandingBalance(BigDecimal outstandingBalance) {
            this.outstandingBalance = outstandingBalance;
        }
        
        public Integer getUnpaidMonthsCount() {
            return unpaidMonthsCount;
        }
        
        public void setUnpaidMonthsCount(Integer unpaidMonthsCount) {
            this.unpaidMonthsCount = unpaidMonthsCount;
        }
    }
    
    /**
     * Calculate expected amount for a student based on their status and current monthly fee
     */
    private BigDecimal calculateExpectedAmount(Long studentId,int month, int year) {
        // For now, we'll use the default monthly fee
        // In a real implementation, you'd fetch the student and calculate based on their status
        return systemSettingService.getMonthlyFee(month,year);
    }

}
