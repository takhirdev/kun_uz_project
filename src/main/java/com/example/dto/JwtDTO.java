package com.example.dto;

import com.example.enums.ProfileRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtDTO {
    private Integer id;
    private String name;
    private ProfileRole role;

    public JwtDTO(Integer id,String name) {
        this.id = id;
        this.name = name;
    }

    public JwtDTO(Integer id, String name, ProfileRole role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }
}
