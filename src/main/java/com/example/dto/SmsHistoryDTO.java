package com.example.dto;

import com.example.enums.SmsStatus;
import com.example.enums.SmsType;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SmsHistoryDTO {
    private Integer id;
    private String phone;
    private String message;
    private LocalDateTime createdDate;

    public SmsHistoryDTO() {
    }
    public SmsHistoryDTO(String phone, String code) {
        this.phone = phone;
        this.message = code;
    }
}
