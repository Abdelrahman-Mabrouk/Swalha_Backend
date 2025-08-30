package com.swalha.dto;

import com.swalha.entity.Student;
import com.swalha.entity.StudentStatus;

import java.time.LocalDate;
import java.util.List;

public class StudentResponse {
    private Long id;
    private String name;
    private String phone;
    private String guardianName;
    private String guardianPhone;
    private LocalDate startedAt;
    private String nationalId;
    private String instructorName;
    private String category;
    private StudentStatus status;
    private ClassInfo classInfo;
    private LocalDate exclusionStart;

    // Inner class for class information
    public static class ClassInfo {
        private Long id;
        private String name;

        public ClassInfo() {}

        public ClassInfo(Long id, String name) {
            this.id = id;
            this.name = name;
        }

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
    }

    public StudentResponse() {}

    public StudentResponse(Student student) {
        this.id = student.getId();
        this.name = student.getName();
        this.phone = student.getphone();
        this.guardianName = student.getGuardianName();
        this.guardianPhone = student.getGuardianPhone();
        this.startedAt = student.getStartedAt();
        this.nationalId = student.getNationalId();
        this.instructorName = student.getInstructorName();
        this.category = student.getCategory();
        this.status = student.getStatus();
        this.exclusionStart = student.getExclusionStart();
        // Set class information if available
        if (student.getClassEntity() != null) {
            this.classInfo = new ClassInfo(
                student.getClassEntity().getId(),
                student.getClassEntity().getName()
            );
        }
    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public LocalDate getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDate startedAt) {
        this.startedAt = startedAt;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public StudentStatus getStatus() {
        return status;
    }

    public void setStatus(StudentStatus status) {
        this.status = status;
    }

    public ClassInfo getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(ClassInfo classInfo) {
        this.classInfo = classInfo;
    }
    public LocalDate getExclusionStart() {
        return exclusionStart;
    }
    public void setExclusionStart(LocalDate exclusionStart) {
        this.exclusionStart = exclusionStart;
    }
}
