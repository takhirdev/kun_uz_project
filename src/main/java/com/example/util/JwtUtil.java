package com.example.util;

import com.example.dto.JwtDTO;
import com.example.enums.ProfileRole;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
@Component
public class JwtUtil {
    @Value("${jwt.token.secretKey}")
    private String secretKey;
    @Value("${jwt.token.validate}")
    private Integer tokenLiveTime;

    public  String generateToken(Integer profileId, String name, ProfileRole role) {
        SignatureAlgorithm signatureAlgorithm  = SignatureAlgorithm.HS512;
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), signatureAlgorithm.getJcaName());

        String token = Jwts.builder()
                .issuedAt(new Date())
                .signWith(secretKeySpec)
                .claim("id", profileId)
                .claim("name", name)
                .claim("role", role)
                .expiration(new Date(System.currentTimeMillis() + tokenLiveTime))
                .issuer("KunUz")
                .compact();
        return token;
    }

    public  JwtDTO decode(String token){
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), signatureAlgorithm.getJcaName());

        JwtParser jwtParser = Jwts.parser()
                .verifyWith(secretKeySpec)
                .build();

        Jws<Claims> jws = jwtParser.parseSignedClaims(token);
        Claims claims = jws.getPayload();

        Integer id = (Integer) claims.get("id");
        String role = (String) claims.get("role");
        String name = (String) claims.get("name");
        if (role != null) {
            ProfileRole profileRole = ProfileRole.valueOf(role);
            return new JwtDTO(id, name, profileRole);
        }
        return new JwtDTO(id, name);
    }
}
