package com.swalha.service.impl;

import com.swalha.dto.MonthlyPaymentDto;
import com.swalha.entity.MonthlyPayment;
import com.swalha.entity.Payment;
import com.swalha.entity.Student;
import com.swalha.entity.StudentStatus;
import com.swalha.repository.MonthlyPaymentRepository;
import com.swalha.repository.PaymentRepository;
import com.swalha.repository.StudentRepository;
import com.swalha.service.PaymentService;
import com.swalha.service.SystemSettingService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SystemSettingService systemSettingService;

    @Autowired
    private MonthlyPaymentRepository monthlyPaymentRepository;
    @Override
    public Payment addPayment(Long studentId, Integer day, Integer month, Integer year, BigDecimal amountPaid, BigDecimal expectedAmount) {
        // Find the student
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));
        Optional<MonthlyPayment> monthlyPayment = monthlyPaymentRepository.findByStudentAndYearAndMonth(student,year,month);
        boolean isPaid;
        if (monthlyPayment.isPresent()) {
            BigDecimal totalAmount = amountPaid.add(monthlyPayment.get().getAmountPaid());
            isPaid= (totalAmount ).compareTo(expectedAmount) == 0;

        }
        else {
            isPaid = amountPaid.compareTo(expectedAmount) == 0;
        }

        Payment newPayment = new Payment(student,day, month, year, amountPaid, expectedAmount, isPaid);
        Payment savedPayment = paymentRepository.save(newPayment);

        updateMonthlyPayment(studentId, month, year);

        return savedPayment;
    }
    @Override
    public List<Payment> getAllPaymentsByStudent(Long studentId) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        if (studentOpt.isEmpty()) {
            throw new RuntimeException("Student not found with ID: " + studentId);
        }

        return paymentRepository.findByStudent(studentOpt.get());
    }
    @Override
    public List<Payment> getPaymentsByStudentAndYear(Long studentId, Integer year) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        if (studentOpt.isEmpty()) {
            throw new RuntimeException("Student not found with ID: " + studentId);
        }

        return paymentRepository.findByStudentAndYear(studentOpt.get(), year);
    }
    @Override
    public List<Payment> getUnpaidPaymentsByStudent(Long studentId) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        if (studentOpt.isEmpty()) {
            throw new RuntimeException("Student not found with ID: " + studentId);
        }

        return paymentRepository.findUnpaidPaymentsByStudent(studentOpt.get());
    }
    @Override
    public BigDecimal getOutstandingBalance(Long studentId) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        if (studentOpt.isEmpty()) {
            throw new RuntimeException("Student not found with ID: " + studentId);
        }

        Student student = studentOpt.get();
        return calculateOutstandingBalance(student);
    }
    @Override
    public Integer getUnpaidMonthsCount(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));

        int unpaidMonthsCount = 0;

        // كل الشهور من يوم ما دخل لحد الشهر الحالي
        List<Integer> months = getStudentMonths(student);

        List<Integer> years = getStudentYears(student);
        for (Integer year : years) {

            for (Integer month : months) {

                List<Payment> payments = paymentRepository.findByStudentAndMonth(student, month);
                System.out.print("Getting expected amount for student: {}, month: {}, year: {} "+ studentId +" "+ month+ " "+year);
                BigDecimal expectedAmount = getExpectedAmountForStudent(student,month,year);

                BigDecimal outstanding;
                if (payments.isEmpty()) {
                    // الشهر ده مدفوعش فيه خالص → مستحق كامل
                    outstanding = expectedAmount;
                } else {
                    // فيه دفعات → احسب المستحق
                    BigDecimal paidThisMonth = payments.stream()
                            .map(Payment::getAmountPaid)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    outstanding = expectedAmount.subtract(paidThisMonth);
                }

                // لو فيه مستحق > 0 يبقى الشهر محسوب كـ "غير مدفوع بالكامل"
                if (outstanding.compareTo(BigDecimal.ZERO) > 0) {
                    unpaidMonthsCount++;
                }
            }
        }
        return unpaidMonthsCount;
    }
    @Override
    public MonthlyPayment getMonthlyPaymentByStudentAndMonth(Long studentId, Integer year, Integer month) {
        return (MonthlyPayment) monthlyPaymentRepository.findByStudentIdAndYearAndMonth(studentId, year, month)
                .orElse(null);
    }
    private BigDecimal getExpectedAmountForStudent(Student student, int month, int year) {
        BigDecimal monthlyFee = systemSettingService.getMonthlyFee(month,year);

        switch (student.getStatus()) {
            case HALF_FEES:
                return monthlyFee.divide(new BigDecimal("2"));
            case FIXED_50:
                return new BigDecimal("50.00");
            case EXEMPT:
                return BigDecimal.ZERO;
            default:
                return monthlyFee;
        }
    }
    private BigDecimal calculateOutstandingBalance(Student student ) {
        BigDecimal totalOutstanding = BigDecimal.ZERO;

        // كل الشهور اللي الطالب المفروض يدفعها (من تاريخ الدخول لحد الشهر الحالي)
        List<Integer> months = getStudentMonths(student);
        List<Integer> years = getStudentYears(student);
        for (Integer year : years) {
            for (Integer month : months) {
                // هات المدفوع للشهر ده (لو مفيش → List فاضية)
                List<Payment> payments = paymentRepository.findByStudentAndMonth(student, month);
                BigDecimal expectedAmount = getExpectedAmountForStudent(student, month, year);


                BigDecimal outstanding;
                if (payments.isEmpty()) {
                    // مفيش أي دفعة → مستحق كامل
                    outstanding = expectedAmount;
                } else {
                    // فيه دفعات → احسب الفرق
                    BigDecimal paidThisMonth = payments.stream()
                            .map(Payment::getAmountPaid)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    outstanding = expectedAmount.subtract(paidThisMonth);
                }

                // ضيف للمجموع لو لسه فيه مستحق
                if (outstanding.compareTo(BigDecimal.ZERO) > 0) {
                    totalOutstanding = totalOutstanding.add(outstanding);
                }
            }
        }

        return totalOutstanding;
    }
    private Integer calculateUnpaidMonthsCount(Student student) {
        List<Payment> unpaidPayments = paymentRepository.findUnpaidPaymentsByStudent(student);
        return unpaidPayments.size();
    }
    private void updateStudentPaymentInfo(Student student) {
        // This method can be used to update any student-specific payment information
        // For now, we'll just save the student to ensure any changes are persisted
        studentRepository.save(student);
    }
    public void excludeStudent(Long studentId, LocalDate start) {
        Student student = studentRepository.findById(studentId).orElseThrow();
        student.setStatus(StudentStatus.EXCLUDED);
        student.setExclusionStart(start);

        List<Payment> payments = paymentRepository.findByStudent(student);
        for (Payment payment : payments) {
            LocalDate paymentDate = LocalDate.of(payment.getYear(), payment.getMonth(), 1);
            if (!paymentDate.isBefore(start)) {
                payment.setNotApplicable(true);
                payment.setPaymentStatus(false);
                paymentRepository.save(payment);
            }
        }
        studentRepository.save(student);
    }
    public MonthlyPayment updateMonthlyPayment(Long studentId, Integer month, Integer year) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Get all history for this month/year
        List<Payment> historyPayments = paymentRepository.findByStudentAndYearAndMonth(student, year, month);

        BigDecimal totalPaid = historyPayments.stream()
                .map(Payment::getAmountPaid)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal expectedAmount = historyPayments.isEmpty() ? BigDecimal.ZERO : historyPayments.get(0).getExpectedAmount();

        boolean paymentStatus = totalPaid.compareTo(expectedAmount) >= 0;

        // Find monthly summary record
        Optional<MonthlyPayment> monthlyOpt = monthlyPaymentRepository.findByStudentAndYearAndMonth(student, year, month);

        MonthlyPayment monthlyPayment;
        if (monthlyOpt.isPresent()) {
            monthlyPayment = monthlyOpt.get();
            monthlyPayment.setAmountPaid(totalPaid);
            monthlyPayment.setPaymentStatus(paymentStatus);
        } else {
            monthlyPayment = new MonthlyPayment(student, month, year, totalPaid, expectedAmount, paymentStatus);
        }

        return monthlyPaymentRepository.save(monthlyPayment);
    }
    @Override
    public List<MonthlyPaymentDto> getMonthlyPaymentsByStudent(Long studentId) {
        return monthlyPaymentRepository.findByStudentId(studentId)
                .stream()
                .map(payment -> {
                    MonthlyPaymentDto dto = new MonthlyPaymentDto();
                    dto.setId(payment.getId());
                    dto.setStudentId(payment.getStudent().getId());
                    dto.setMonth(payment.getMonth());
                    dto.setYear(payment.getYear());
                    dto.setAmountPaid(payment.getAmountPaid());
                    dto.setExpectedAmount(payment.getExpectedAmount());
                    dto.setPaymentStatus(payment.getPaymentStatus());
                    return dto;
                })
                .collect(Collectors.toList());


    }
    public List<Integer> getStudentMonths(Student student) {
        LocalDate start = student.getStartedAt();
        LocalDate now = LocalDate.now();

        // نتاكد انه نفس السنة
        if (start.getYear() != now.getYear()) {
            throw new IllegalArgumentException("السنة مختلفة، لازم الطالب يكون داخل نفس السنة الحالية");
        }

        int startMonth = start.getMonthValue();
        int endMonth = now.getMonthValue();

        List<Integer> months = new ArrayList<>();

        for (int m = startMonth; m <= endMonth; m++) {
            months.add(m);
        }


        return months;
    }
    public List<Integer> getStudentYears(Student student) {
        LocalDate start = student.getStartedAt();
        LocalDate now = LocalDate.now();

        // نتاكد انه نفس السنة
        if (start.getYear() != now.getYear()) {
            throw new IllegalArgumentException("السنة مختلفة، لازم الطالب يكون داخل نفس السنة الحالية");
        }


        int startYear = start.getYear();
        int endYear = now.getYear();

        List<Integer> years = new ArrayList<>();


        for (int m = startYear; m <= endYear; m++) {
            years.add(m);
        }

        return years;
    }



}