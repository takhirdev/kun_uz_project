package com.example.controller;

import com.example.dto.auth.RegistrationDTO;
import com.example.dto.profile.ProfileDTO;
import com.example.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<String> auth(@Valid @RequestBody RegistrationDTO dto) {
        String body = authService.authorization(dto);
        return ResponseEntity.ok().body(body);
    }

    @GetMapping("/verification/{userId}")
    public ResponseEntity<String> verification(@PathVariable("userId") Integer userId) {
        String body = authService.authorizationVerification(userId);
        return ResponseEntity.ok().body(body);
    }

    @PostMapping("/login")

    public ResponseEntity<ProfileDTO> login(@RequestParam String email,
                                            @RequestParam String password) {
        ProfileDTO response = authService.login(email, password);
        return ResponseEntity.ok().body(response);
    }
}
