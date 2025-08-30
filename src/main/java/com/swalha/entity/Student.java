package com.swalha.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;


@Entity
@Table(name = "students")
public class Student {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    

    @NotBlank
    @Column(nullable = false)
    private String name;
    
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", nullable = false)
    @JsonBackReference
    private Class classEntity;

    private String phone;
    
    @Column(name = "guardian_name", nullable = false)
    private String guardianName;
    
    @Column(name = "guardian_phone", nullable = false)
    private String guardianPhone;
    
    @Column(name = "startedAt")
    private LocalDate startedAt;
    

    
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Attendance> attendances;
    
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Payment> payments;
    
    private String nationalId;
    @NotBlank
    public String instructorName;


    @NotBlank(message = "Category must not be blank")
    private String category;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StudentStatus status = StudentStatus.NORMAL;

    LocalDate exclusionStart;
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Class getClassEntity() {
        return classEntity;
    }
    
    public void setClassEntity(Class classEntity) {
        this.classEntity = classEntity;
    }
    
    public String getGuardianName() {
        return guardianName;
    }
    
    public void setGuardianName(String guardianName) {
        this.guardianName = guardianName;
    }
    
    public String getGuardianPhone() {
        return guardianPhone;
    }
    
    public void setGuardianPhone(String guardianPhone) {
        this.guardianPhone = guardianPhone;
    }

    public List<Attendance> getAttendances() {
        return attendances;
    }
    
    public void setAttendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }
    
    public List<Payment> getPayments() {
        return payments;
    }
    
    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }
    
    public StudentStatus getStatus() {
        return status;
    }
    
    public void setStatus(StudentStatus status) {
        this.status = status;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    public String getCategory() {
        return category;
    }
    public String getphone() {
        return phone;
    }
    public void setphone(String phone) {
        this.phone = phone;
    }
    public String  getNationalId(
    ){
        return nationalId;
    }
    public void setNationalId(String nationalId){
        this.nationalId=nationalId;
    }
    public void setStartedAt(LocalDate startedAt){
        this.startedAt=startedAt;
    }
    public String getInstructorName(){
        return instructorName;
    }
    public LocalDate getStartedAt(){
        return startedAt;
    }
    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", nationalId='" + nationalId + '\'' +
                ", classEntity=" + (classEntity != null ? classEntity.getId() : "null") +
                ", instructorName=" + instructorName +
                ", category=" + category +
                ", status=" + status +
                ", guardianName=" + guardianName +
                ", guardianPhone=" + guardianPhone +
                ", startedAt=" + startedAt +
                ", attendances=" + attendances +
                ", payments=" + payments +
                '}';
    }
    public void setInstructorName(String instructorName){
        this.instructorName=instructorName;
    }

    public void setExclusionStart(LocalDate start) {
        this.exclusionStart=start;
    }

    public LocalDate getExclusionStart() {
        return exclusionStart;
    }


}