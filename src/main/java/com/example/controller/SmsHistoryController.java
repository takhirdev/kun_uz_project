package com.example.controller;

import com.example.dto.EmailHistoryDTO;
import com.example.dto.SmsHistoryDTO;
import com.example.service.SmsHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/sms")
public class SmsHistoryController {
    @Autowired
    private SmsHistoryService smsHistoryService;

    @GetMapping("/byPhone")
    public ResponseEntity<List<SmsHistoryDTO>> getByPhone(@RequestParam String phone) {
        List<SmsHistoryDTO> dtoList = smsHistoryService.getByPhone(phone);
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/givenDate")
    public ResponseEntity<List<SmsHistoryDTO>> getAllByGivenDate(@RequestParam LocalDate date) {
        List<SmsHistoryDTO> dtoList = smsHistoryService.getAllByGivenDate(date);
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/admin/pagination")
    public ResponseEntity<Page<SmsHistoryDTO>> pagination(@RequestParam Integer pageNumber,
                                                          @RequestParam Integer pageSize) {
        Page<SmsHistoryDTO> page = smsHistoryService.pagination(pageNumber - 1, pageSize);
        return ResponseEntity.ok(page);
    }
}
