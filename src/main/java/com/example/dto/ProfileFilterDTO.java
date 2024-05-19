package com.example.dto;

import com.example.enums.Role;
import com.example.enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class ProfileFilterDTO {
    private String name;
    private String surname;
    private String phone;
    private Role role;
    private LocalDateTime createdDateFrom;
    private LocalDateTime createdDateTo;
}
