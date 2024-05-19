package com.example.dto;

import com.example.enums.Role;
import com.example.enums.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class ProfileCreateDTO {
    @NotBlank(message = "name reqiured")
    private String name;
    @NotBlank(message = "surname required")
    private String surname;
    @Email(message = "invalid email")
    private String email;
    @NotBlank(message = "phone required")
    @Size(min = 12 , max = 12,message = "phone must be 12 numbers")
    private String phone;
    @Size(min = 5, message = "password more than 5")
    private String password;
}
