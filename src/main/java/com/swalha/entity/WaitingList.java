package com.swalha.entity;

import com.swalha.dto.WaitingListDto;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "waiting_list")
public class WaitingList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // بيانات الطالب
    @Column(name = "student_name")
    private String studentName;

    @Column(name = "age")
    private Integer age;

    private String qualification;
    private String national_id;
    private LocalDate enrollment_date;
    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "job")
    private String job;

    private String employer;

    @Column(name = "address")
    private String address;

    // بيانات اختبار القبول
    @Column(name = "previous_memorization_amount")
    private String previousMemorizationAmount;

    @Column(name = "level")
    private String level;

    @Column(name = "examiner_name")
    private String examinerName;

    // بيانات الإدارية
     private LocalDate day_of_attendance;
     private String specific_time;

    @Column(name = "expenses")
    private String expenses;

    @Column(name = "receiver")
    private String receiver;

    @Column(name = "submission_date")
    private LocalDate submissionDate;
    private String category;

    // Constructors
    public WaitingList() {}

    public WaitingList(String studentName, Integer age,
                       String job , String employer  , String phoneNumber, String mobileNumber,
                       String address,String qualification, String national_id , String day_of_attendance, String specific_time, String expenses, String receiver, String submissionDate, String level,LocalDate enrollment_date,String category) {
        this.studentName = studentName;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.mobileNumber = mobileNumber;
        this.address = address;
        this.qualification = qualification;
        this.national_id = national_id;
        this.day_of_attendance = LocalDate.parse(day_of_attendance);
        this.specific_time = specific_time;
        this.expenses = expenses;
        this.receiver = receiver;
        this.submissionDate = LocalDate.parse(submissionDate);
        this.level = level;
        this.employer = employer;
        this.examinerName = examinerName;
        this.enrollment_date = enrollment_date;
        this.category = category;

    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }





    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPreviousMemorizationAmount() {
        return previousMemorizationAmount;
    }

    public void setPreviousMemorizationAmount(String previousMemorizationAmount) {
        this.previousMemorizationAmount = previousMemorizationAmount;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getExaminerName() {
        return examinerName;
    }

    public void setExaminerName(String examinerName) {
        this.examinerName = examinerName;
    }

    public String getExpenses() {
        return expenses;
    }

    public void setExpenses(String expenses) {
        this.expenses = expenses;
    }


    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public LocalDate getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDate submissionDate) {
        this.submissionDate = submissionDate;
    }
    public LocalDate getEnrollment_date() {
        return enrollment_date;
    }
    public void setEnrollment_date(LocalDate enrollment_date) {
        this.enrollment_date = enrollment_date;
    }
    public String getSpecific_time() {
        return specific_time;
    }
    public void setSpecific_time(String specific_time) {
        this.specific_time = specific_time;
    }
    public String getNational_id() {
        return national_id;
    }
    public void setNational_id(String national_id) {
        this.national_id = national_id;
    }
    public String getJob() {
        return job;
    }
    public void setJob(String job) {
        this.job = job;
    }
    public String getEmployer() {
        return employer;
    }
    public void setEmployer(String employer) {
        this.employer = employer;
    }
    public void setObject(WaitingListDto object) {
        this.studentName = object.getStudentName();
        this.age = object.getAge();
        this.phoneNumber = object.getPhoneNumber();
        this.mobileNumber = object.getMobileNumber();
        this.address = object.getAddress();
        this.previousMemorizationAmount = object.getPreviousMemorizationAmount();
        this.level = object.getLevel();
        this.examinerName = object.getExaminerName();
        this.expenses = object.getExpenses();
        this.receiver = object.getReceiver();
        this.submissionDate = object.getSubmissionDate();
        this.enrollment_date = object.getEnrollment_date();
        this.specific_time = object.getSpecific_time();
        this.national_id = object.getNational_id();
        this.job = object.getJob();
        this.employer = object.getEmployer();
        this.category=object.getCategory();
        this.qualification = object.getQualification();

    }

    @Override
    public String toString() {
        return "WaitingList{" +
                "id=" + id +
                ", studentName='" + studentName + '\'' +
                ", age=" + age +
                ", qualification='" + qualification + '\'' +
                ", national_id='" + national_id + '\'' +
                ", enrollment_date=" + enrollment_date +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", job='" + job + '\'' +
                ", employer='" + employer + '\'' +
                ", address='" + address + '\'' +
                ", previousMemorizationAmount='" + previousMemorizationAmount + '\'' +
                ", level='" + level + '\'' +
                ", examinerName='" + examinerName + '\'' +
                ", day_of_attendance=" + day_of_attendance +
                ", specific_time='" + specific_time + '\'' +
                ", expenses='" + expenses + '\'' +
                ", receiver='" + receiver + '\'' +
                ", submissionDate=" + submissionDate +
                '}';
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getCategory() {
        return category;
    }
    public void setQualification(String qualification) {
        this.qualification = qualification;
    }
    public String getQualification() {
        return qualification;
    }

}