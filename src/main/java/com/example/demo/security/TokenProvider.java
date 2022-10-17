package com.example.demo.security;

import com.example.demo.model.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
public class TokenProvider {
    private static final String SECRET_KEY = "123456789";

    public String create(UserEntity userEntity) {
        Date expiryDate = Date.from(
                Instant.now().
                plus(1, ChronoUnit.DAYS));

        // JWT Token
        return Jwts.builder()
                // 헤더에 들어갈 내용 및 시크릿키
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                // 페이로드에 들어갈 내용
                .setSubject(userEntity.getId()) // sub
                .setIssuer("todo-app")          // iss
                .setIssuedAt(new Date())        // iat
                .setExpiration(expiryDate)      // exp
                .compact();
    }

    public String validateAndGetUserId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}
