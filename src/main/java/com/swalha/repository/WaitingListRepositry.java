package com.swalha.repository;

import com.swalha.dto.WaitingListDto;
import com.swalha.entity.WaitingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WaitingListRepositry extends JpaRepository<WaitingList, Long> {
    WaitingList findByStudentName(String studentName);


}
