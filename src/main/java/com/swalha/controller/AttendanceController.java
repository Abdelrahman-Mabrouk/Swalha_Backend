package com.swalha.controller;

import com.swalha.entity.Attendance;
import com.swalha.entity.Student;
import com.swalha.repository.AttendanceRepository;
import com.swalha.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/registrar/attendance")
@CrossOrigin(origins = "http://localhost:5173")
public class AttendanceController {
    
    @Autowired
    private AttendanceRepository attendanceRepository;
    
    @Autowired
    private StudentRepository studentRepository;
    
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'REGISTRAR')")
    public ResponseEntity<List<Attendance>> getAllAttendance() {
        List<Attendance> attendance = attendanceRepository.findAll();
        return ResponseEntity.ok(attendance);
    }
    
    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'REGISTRAR')")
    public ResponseEntity<List<Attendance>> getStudentAttendance(@PathVariable Long studentId) {
        List<Attendance> attendance = attendanceRepository.findByStudentId(studentId);
        return ResponseEntity.ok(attendance);
    }
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'REGISTRAR')")
    public ResponseEntity<Attendance> createAttendance(@RequestBody Attendance attendance) {
        if (attendance.getStudent() != null && attendance.getStudent().getId() != null) {
            Optional<Student> student = studentRepository.findById(attendance.getStudent().getId());
            if (student.isPresent()) {
                attendance.setStudent(student.get());
            }
        }
        
        if (attendance.getDate() == null) {
            attendance.setDate(LocalDate.now());
        }
        
        Attendance savedAttendance = attendanceRepository.save(attendance);
        return ResponseEntity.ok(savedAttendance);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'REGISTRAR')")
    public ResponseEntity<Attendance> updateAttendance(@PathVariable Long id, @RequestBody Attendance attendance) {
        Optional<Attendance> existingAttendance = attendanceRepository.findById(id);
        if (existingAttendance.isPresent()) {
            Attendance updatedAttendance = existingAttendance.get();
            updatedAttendance.setDate(attendance.getDate());
            updatedAttendance.setPresent(attendance.isPresent());
            updatedAttendance.setNotes(attendance.getNotes());
            
            if (attendance.getStudent() != null && attendance.getStudent().getId() != null) {
                Optional<Student> student = studentRepository.findById(attendance.getStudent().getId());
                if (student.isPresent()) {
                    updatedAttendance.setStudent(student.get());
                }
            }
            
            Attendance savedAttendance = attendanceRepository.save(updatedAttendance);
            return ResponseEntity.ok(savedAttendance);
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'REGISTRAR')")
    public ResponseEntity<Void> deleteAttendance(@PathVariable Long id) {
        if (attendanceRepository.existsById(id)) {
            attendanceRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
