package com.example.controller;

import com.example.dto.SmsHistoryDTO;
import com.example.dto.auth.RegistrationDTO;
import com.example.dto.auth.LoginDTO;
import com.example.dto.profile.ProfileDTO;
import com.example.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/registrationWithEmail")
    public ResponseEntity<HttpStatus> registrWithEmail(@Valid @RequestBody RegistrationDTO dto) {
        authService.registrWithEmail(dto);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }
    @PostMapping("/registrationWithPhone")
    public ResponseEntity<HttpStatus> registrWithPhone(@Valid @RequestBody RegistrationDTO dto) {
        authService.registrWithPhone(dto);
        return ResponseEntity.ok().body(HttpStatus.OK);
    }

    @GetMapping("/verificationWithEmail/{userId}")
    public ResponseEntity<String> verifyWithEmail(@PathVariable Integer userId) {
        String body = authService.verifyWithEmail(userId);
        return ResponseEntity.ok().body(body);
    }
    @PostMapping("/verificationWithSms")
    public ResponseEntity<String> verifyWithSms(@RequestBody SmsHistoryDTO dto) {
        String body = authService.verifyWithSms(dto);
        return ResponseEntity.ok().body(body);
    }

    @GetMapping("/registration/resendEmail/{email}")
    public ResponseEntity<String> resendEmail(@PathVariable String email) {
        String body = authService.resendEmail(email);
        return ResponseEntity.ok().body(body);
    }
    @GetMapping("/registration/resendSms/{phone}")
    public ResponseEntity<String> resendSms(@PathVariable String phone) {
        String body = authService.resendSms(phone);
        return ResponseEntity.ok().body(body);
    }


    @PostMapping("/loginWithEmail")
    public ResponseEntity<ProfileDTO> loginWithEmail(@Valid @RequestBody LoginDTO dto) {
        ProfileDTO response = authService.loginWithEmail(dto);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/loginWithPhone")
    public ResponseEntity<ProfileDTO> loginWithPhone(@Valid @RequestBody LoginDTO dto) {
        ProfileDTO response = authService.loginWithPhone(dto);
        return ResponseEntity.ok().body(response);
    }
}
