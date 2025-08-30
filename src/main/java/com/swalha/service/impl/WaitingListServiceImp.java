package com.swalha.service.impl;

import com.swalha.dto.WaitingListDto;
import com.swalha.entity.WaitingList;
import com.swalha.repository.WaitingListRepositry;
import com.swalha.service.WaitingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class WaitingListServiceImp implements WaitingListService {
    @Autowired
    public WaitingListRepositry waitingListRepositry;
    @Override
    public ResponseEntity add(WaitingListDto waitingListDto) {
        WaitingList testStudent = waitingListRepositry.findByStudentName(waitingListDto.getStudentName());
        if (testStudent != null) {
            throw  new RuntimeException("this student is already exsit");
        }
        testStudent = new WaitingList();
        testStudent.setObject(waitingListDto);
        waitingListRepositry.save(testStudent);
        return ResponseEntity.ok("Student add succefuly");
    }

    @Override
    public ResponseEntity edit(WaitingListDto waitingListDto) {
        WaitingList testStudent = waitingListRepositry.findByStudentName(waitingListDto.getStudentName());
        if (testStudent == null) {
          throw   new RuntimeException("this student does not exist");
        }
        testStudent.setObject(waitingListDto);
        waitingListRepositry.save(testStudent);
        return ResponseEntity.ok("Student edit succefuly");
    }

    @Override
    public ResponseEntity delete(WaitingListDto waitingListDto) {
        WaitingList testStudent = waitingListRepositry.findByStudentName(waitingListDto.getStudentName());
        if (testStudent == null) {
            throw  new RuntimeException("this student does not exist");
        }
        waitingListRepositry.delete(testStudent);
        return ResponseEntity.ok("Student deleted successfully");

    }

    @Override
    public List<WaitingList> getWaitingList() {
        return waitingListRepositry.findAll();
    }
}
