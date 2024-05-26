package com.example.dto.profile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
    @Email(message = "invalid email")
    private String email;
    @Size(min = 12 , max = 12,message = "phone must be 12 numbers")
    private String phone;
    @Size(min = 5, message = "password more than 5")
    private String password;
}
