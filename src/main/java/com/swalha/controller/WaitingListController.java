package com.swalha.controller;

import com.swalha.dto.WaitingListDto;
import com.swalha.entity.WaitingList;
import com.swalha.service.WaitingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/waitinglist")
public class WaitingListController {
    @Autowired
    private WaitingListService waitingListService;

    @PostMapping
    public ResponseEntity addStudentToWaitingList(@RequestBody WaitingListDto waitingList){
        return waitingListService.add(waitingList);
    }
    @GetMapping
    public ResponseEntity<List<WaitingList>> getWaitingList(){
        return ResponseEntity.ok(waitingListService.getWaitingList());
    }
    @PutMapping
    public ResponseEntity editStudentTOnWaitingList(@RequestBody WaitingListDto waitingList){
        return waitingListService.edit(waitingList);
    }
    @DeleteMapping
    public ResponseEntity deleteStudentFromWaitingList(@RequestBody WaitingListDto waitingList){
        return  waitingListService.delete(waitingList);
    }

}
