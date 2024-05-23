package com.example.dto.profile;

import com.example.enums.ProfileRole;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class ProfileFilterDTO {
    private String name;
    private String surname;
    private String phone;
    private ProfileRole profileRole;
    private LocalDateTime createdDateFrom;
    private LocalDateTime createdDateTo;
}
