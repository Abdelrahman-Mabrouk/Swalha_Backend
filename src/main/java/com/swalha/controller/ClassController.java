package com.swalha.controller;

import com.swalha.entity.Class;
import com.swalha.repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/registrar/classes")
@CrossOrigin(origins = "http://localhost:5173")
public class ClassController {
    
    @Autowired
    private ClassRepository classRepository;
    
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'REGISTRAR')")
    public ResponseEntity<List<Class>> getAllClasses() {
        List<Class> classes = classRepository.findAll();
        return ResponseEntity.ok(classes);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'REGISTRAR')")
    public ResponseEntity<Class> getClassById(@PathVariable Long id) {
        Optional<Class> classEntity = classRepository.findById(id);
        return classEntity.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'REGISTRAR')")
    public ResponseEntity<Class> createClass(@RequestBody Class classEntity) {
        Class savedClass = classRepository.save(classEntity);
        return ResponseEntity.ok(savedClass);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'REGISTRAR')")
    public ResponseEntity<Class> updateClass(@PathVariable Long id, @RequestBody Class classEntity) {
        Optional<Class> existingClass = classRepository.findById(id);
        if (existingClass.isPresent()) {
            Class updatedClass = existingClass.get();
            updatedClass.setName(classEntity.getName());
            updatedClass.setAcademicYear(classEntity.getAcademicYear());
            Class savedClass = classRepository.save(updatedClass);
            return ResponseEntity.ok(savedClass);
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'REGISTRAR')")
    public ResponseEntity<Void> deleteClass(@PathVariable Long id) {
        if (classRepository.existsById(id)) {
            classRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
