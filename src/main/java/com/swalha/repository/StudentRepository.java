package com.swalha.repository;

import com.swalha.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    

    
    List<Student> findByClassEntityId(Long classId);
    
    List<Student> findByNameContainingIgnoreCase(String name);
    
    @Query("SELECT s FROM Student s WHERE s.classEntity.id = :classId AND s.name LIKE %:name%")
    List<Student> findByClassAndName(@Param("classId") Long classId, @Param("name") String name);
    
}