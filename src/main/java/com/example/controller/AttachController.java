package com.example.controller;

import com.example.dto.AttachDTO;
import com.example.dto.EmailHistoryDTO;
import com.example.service.AttachService;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/attach")
public class AttachController {

    private final AttachService attachService;

    public AttachController(AttachService attachService) {
        this.attachService = attachService;
    }

    @PostMapping("/upload")
    public ResponseEntity<AttachDTO> upload(@RequestParam("file") MultipartFile file) {
        AttachDTO response = attachService.upload(file);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/open_general/{fileName}", produces = MediaType.ALL_VALUE)
    public byte[] open_general(@PathVariable("fileName") String fileName) {
        return attachService.open_general(fileName);
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> download(@PathVariable("fileName") String fileName) {
        Resource file = attachService.download(fileName);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @DeleteMapping( "/delete/{id}")
    public ResponseEntity<HttpStatus> delete (@PathVariable String id) {
        attachService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
//////// pagination
    @GetMapping("pagination")
    public ResponseEntity<Page<AttachDTO>> pagination(@RequestParam int pageNumber,
                                                      @RequestParam int pageSize) {
        Page<AttachDTO> page = attachService.pagination(pageNumber-1,pageSize);
        return ResponseEntity.ok(page);
    }
}
