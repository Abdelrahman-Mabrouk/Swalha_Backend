package com.swalha.controller;

import com.swalha.dto.StudentResponse;
import com.swalha.entity.Student;
import com.swalha.repository.StudentRepository;
import com.swalha.repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/registrar/students")
@CrossOrigin(origins = "http://localhost:5173")
public class StudentController {
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private ClassRepository classRepository;
    
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'REGISTRAR')")
    public ResponseEntity<List<StudentResponse>> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        List<StudentResponse> studentResponses = students.stream()
            .map(StudentResponse::new)
            .collect(Collectors.toList());
        return ResponseEntity.ok(studentResponses);
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'REGISTRAR')")
    public ResponseEntity<StudentResponse> getStudentById(@PathVariable Long id) {
        Optional<Student> student = studentRepository.findById(id);
        return student.map(s -> ResponseEntity.ok(new StudentResponse(s)))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'REGISTRAR')")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        System.out.println("//////////////////////////////////////////////");
        System.out.println("Received student: " + student.toString());

        if (student.getClassEntity() != null && student.getClassEntity().getId() != null) {
            Optional<com.swalha.entity.Class> classEntity = classRepository.findById(student.getClassEntity().getId());
            if (classEntity.isPresent()) {
                student.setClassEntity(classEntity.get());
            }
        }
        Student savedStudent = studentRepository.save(student);
        return ResponseEntity.ok(savedStudent);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'REGISTRAR')")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        Optional<Student> existingStudent = studentRepository.findById(id);
        if (existingStudent.isPresent()) {
            Student updatedStudent = existingStudent.get();
            updatedStudent.setName(student.getName());
            updatedStudent.setGuardianName(student.getGuardianName());
            updatedStudent.setGuardianPhone(student.getGuardianPhone());
            updatedStudent.setCategory(student.getCategory());
            updatedStudent.setStatus(student.getStatus());
            updatedStudent.setClassEntity(student.getClassEntity());
            updatedStudent.setExclusionStart(student.getExclusionStart());
            updatedStudent.setPayments(student.getPayments());
            updatedStudent.setInstructorName(student.getInstructorName());
            
            if (student.getClassEntity() != null && student.getClassEntity().getId() != null) {
                Optional<com.swalha.entity.Class> classEntity = classRepository.findById(student.getClassEntity().getId());
                if (classEntity.isPresent()) {
                    updatedStudent.setClassEntity(classEntity.get());
                }
            }
            
            Student savedStudent = studentRepository.save(updatedStudent);
            return ResponseEntity.ok(savedStudent);
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'REGISTRAR')")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
