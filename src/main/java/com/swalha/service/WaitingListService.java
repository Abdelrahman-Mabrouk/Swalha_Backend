package com.swalha.service;

import com.swalha.dto.WaitingListDto;
import com.swalha.entity.WaitingList;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
public interface WaitingListService {
    ResponseEntity add(WaitingListDto waitingListDto);
    ResponseEntity edit(WaitingListDto waitingListDto);
    ResponseEntity delete(WaitingListDto waitingListDto);
    List<WaitingList> getWaitingList();
}
