package com.example.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginDTO {
    @Email(message = "invalid email")
    @NotBlank(message = "email required")
    private String email;
    @NotBlank(message = "password required")
    @Size(min = 5, message = "password length minimum 5 characters")
    private String password;
}
