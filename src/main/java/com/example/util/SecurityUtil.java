package com.example.util;

import com.example.dto.JwtDTO;
import com.example.enums.ProfileRole;
import com.example.exception.AppForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {
    @Autowired
    private JwtUtil jwtUtil;

    public  JwtDTO getJwtDTO(String token) {
        String jwt = token.substring(7); // Bearer eyJhb
        return jwtUtil.decode(jwt);
    }
    public  JwtDTO getJwtDTO(String token, ProfileRole requiredRole) {
        JwtDTO dto = getJwtDTO(token);
        if(!dto.getRole().equals(requiredRole)){
            throw new AppForbiddenException("Method not allowed");
        }
        return dto;
    }
}
