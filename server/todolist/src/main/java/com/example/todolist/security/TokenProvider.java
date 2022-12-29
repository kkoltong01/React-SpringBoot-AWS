package com.example.todolist.security;

import com.example.todolist.model.UserEntity;
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

    private static final String SECRET_KEY = "alksenflkasndlkfnsldf";

    public String create(UserEntity userEntity) {
        //기한 1일로 설정
        Date expiryDate = Date.from(
                Instant.now()
                        .plus(1, ChronoUnit.DAYS)
        );

        return Jwts.builder()
                //header에 들어갈 내용 및 서명을 위한 키
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                //PAYLOAD에 들어갈 내용
                .setSubject(userEntity.getId())
                .setIssuer("todo app")
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .compact();
    }

    public String validateAndGetUserId(String token) {
        //parseClaimsJws 메서드가 base 64로 디코딩 및 파싱
        //헤더와 페이로드를 setSigningKey로 넘어온 시크릿을 이용해 서명 후, token의 서명과 비교
        //위조되지 않았다면 페이로드(Claims) 리턴, 위조라면 예외를 날림
        // userId가 필요하므로 getBody를 부름
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}
