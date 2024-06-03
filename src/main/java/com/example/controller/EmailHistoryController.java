package com.example.controller;

import com.example.dto.EmailHistoryDTO;
import com.example.service.EmailHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/email")
public class EmailHistoryController {
        @Autowired
        private EmailHistoryService emailHistoryService;

    @GetMapping("/byEmail")
    public ResponseEntity<List<EmailHistoryDTO>> getAllByEmail(@RequestParam String email) {
        List<EmailHistoryDTO> dtoList = emailHistoryService.getAllByEmail(email);
        return ResponseEntity.ok(dtoList);
    }
    @GetMapping("/givenDate")
    public ResponseEntity<List<EmailHistoryDTO>> getAllByGivenDate(@RequestParam LocalDate date) {
        List<EmailHistoryDTO> dtoList = emailHistoryService.getAllByGivenDate(date);
        return ResponseEntity.ok(dtoList);
    }
    @GetMapping("/admin/pagination")
    public ResponseEntity<Page<EmailHistoryDTO>> pagination (@RequestParam Integer pageNumber,
                                                             @RequestParam Integer pageSize) {
        Page<EmailHistoryDTO> page = emailHistoryService.pagination(pageNumber-1, pageSize);
        return ResponseEntity.ok(page);
    }
}
