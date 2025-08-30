package com.swalha.repository;

import com.swalha.entity.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassRepository extends JpaRepository<Class, Long> {
    
    List<Class> findByAcademicYear(String academicYear);
    
    List<Class> findByNameContainingIgnoreCase(String name);
    Class findByName(String name);
} 