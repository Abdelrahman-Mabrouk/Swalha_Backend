package com.swalha.config;

import com.swalha.entity.*;
import com.swalha.entity.Class;
import com.swalha.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class DataSeeder implements CommandLineRunner {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ClassRepository classRepository;
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private RevenueRepository revenueRepository;
    
    @Autowired
    private ExpenseRepository expenseRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        // Only seed if no users exist
        if (userRepository.count() == 0) {
            seedUsers();
            seedClasses();
            seedStudents();
            seedRevenues();
            seedExpenses();
        }
    }
    
    private void seedUsers() {
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setFullName("System Administrator");
        admin.setRole(UserRole.ADMIN);
        userRepository.save(admin);
        
        User registrar = new User();
        registrar.setUsername("registrar");
        registrar.setPassword(passwordEncoder.encode("registrar123"));
        registrar.setFullName("School Registrar");
        registrar.setRole(UserRole.REGISTRAR);
        userRepository.save(registrar);
        
        User accountant = new User();
        accountant.setUsername("accountant");
        accountant.setPassword(passwordEncoder.encode("accountant123"));
        accountant.setFullName("School Accountant");
        accountant.setRole(UserRole.ACCOUNTANT);
        userRepository.save(accountant);
    }
    
    private void seedClasses() {
        Class class1 = new Class();
        class1.setName("Class 1A");
        class1.setAcademicYear("2024-2025");
        classRepository.save(class1);
        
        Class class2 = new Class();
        class2.setName("Class 2A");
        class2.setAcademicYear("2024-2025");
        classRepository.save(class2);
        
        Class class3 = new Class();
        class3.setName("Class 3A");
        class3.setAcademicYear("2024-2025");
        classRepository.save(class3);
    }
    
    private void seedStudents() {
        Class class1 = classRepository.findByName("Class 1A");
        Class class2 = classRepository.findByName("Class 2A");
        
        Student student1 = new Student();
        student1.setName("Ahmed Hassan");
        student1.setClassEntity(class1);
        student1.setGuardianName("Hassan Ali");
        student1.setCategory("Men");
        student1.setGuardianPhone("+201234567890");
        studentRepository.save(student1);
        
        Student student2 = new Student();
        student2.setName("Fatima Mohamed");
        student2.setClassEntity(class1);
        student2.setGuardianName("Mohamed Ahmed");
        student2.setGuardianPhone("+201234567891");
        student2.setCategory("Women");
        studentRepository.save(student2);
        
        Student student3 = new Student();
        student3.setName("Omar Khalil");
        student3.setClassEntity(class2);
        student3.setGuardianName("Khalil Omar");
        student3.setGuardianPhone("+201234567892");
        student3.setCategory("children");
        studentRepository.save(student3);
    }
    
    private void seedRevenues() {
        Revenue revenue1 = new Revenue();
        revenue1.setName("Student Registration Fee");
        revenue1.setAmount(new BigDecimal("500.00"));
        revenue1.setMonth("January 2024");
        revenue1.setCategory(RevenueCategory.DOCUMENTS);
        revenueRepository.save(revenue1);
        
        Revenue revenue2 = new Revenue();
        revenue2.setName("Book Sales");
        revenue2.setAmount(new BigDecimal("300.00"));
        revenue2.setMonth("January 2024");
        revenue2.setCategory(RevenueCategory.BOOKS);
        revenueRepository.save(revenue2);
        
        Revenue revenue3 = new Revenue();
        revenue3.setName("Donation from Parent");
        revenue3.setAmount(new BigDecimal("1000.00"));
        revenue3.setMonth("January 2024");
        revenue3.setCategory(RevenueCategory.DONATION);
        revenueRepository.save(revenue3);
    }
    
    private void seedExpenses() {
        Expense expense1 = new Expense();
        expense1.setName("Classroom Maintenance");
        expense1.setAmount(new BigDecimal("800.00"));
        expense1.setCategory(ExpenseCategory.MAINTENANCE);
        expense1.setSignature("Admin");
        expenseRepository.save(expense1);
        
        Expense expense2 = new Expense();
        expense2.setName("Office Supplies");
        expense2.setAmount(new BigDecimal("200.00"));
        expense2.setCategory(ExpenseCategory.OTHER);
        expense2.setSignature("Admin");
        expenseRepository.save(expense2);
        
        Expense expense3 = new Expense();
        expense3.setName("Teacher Loans");
        expense3.setAmount(new BigDecimal("500.00"));
        expense3.setCategory(ExpenseCategory.LOANS);
        expense3.setSignature("Admin");
        expenseRepository.save(expense3);
    }
} 