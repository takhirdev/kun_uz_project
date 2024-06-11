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
    public ResponseEntity<String> registrWithEmail(@Valid @RequestBody RegistrationDTO dto) {
        String response = authService.registrWithEmail(dto);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/registrationWithPhone")
    public ResponseEntity<String> registrWithPhone(@Valid @RequestBody RegistrationDTO dto) {
        String response = authService.registrWithPhone(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/verificationWithEmail/{userId}")
    public ResponseEntity<String> verifyWithEmail(@PathVariable Integer userId) {
        String response = authService.verifyWithEmail(userId);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/verificationWithSms")
    public ResponseEntity<String> verifyWithSms(@RequestBody SmsHistoryDTO dto) {
        String response = authService.verifyWithSms(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/registration/resendEmail/{email}")
    public ResponseEntity<String> resendEmail(@PathVariable String email) {
        String response = authService.resendEmail(email);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/registration/resendSms/{phone}")
    public ResponseEntity<String> resendSms(@PathVariable String phone) {
        String response = authService.resendSms(phone);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/loginWithEmail")
    public ResponseEntity<ProfileDTO> loginWithEmail(@Valid @RequestBody LoginDTO dto) {
        ProfileDTO response = authService.loginWithEmail(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/loginWithPhone")
    public ResponseEntity<ProfileDTO> loginWithPhone(@Valid @RequestBody LoginDTO dto) {
        ProfileDTO response = authService.loginWithPhone(dto);
        return ResponseEntity.ok(response);
    }
}
