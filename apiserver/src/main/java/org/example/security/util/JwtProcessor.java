package org.example.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtProcessor {
//    토큰 유효기간을 10분으로 설정
    static private final long TOKEN_VALID_MILISECOND = 1000L*60*10;

//    비밀 키 설정 개발시에는 서버 재기동하는 경우가 많으므로 임의의 문자열
//    이정도로 길게 할것ㅋㅋ
    private String secretKey ="5bR7qC1kV2hL9mXnA3tWzF0oE8jY4rPfjdhvncjrgnmfjdshwgbere75tuhdjur4ytufdhbdfngju367896584uyrhfju37ryfhjd";
    private Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
// 비밀키 설정 매번 변경되므로 운영시에만 사용
//    private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)  //주로 사용자 식별용
                .setIssuedAt(new Date()) //JWT 발급 시간
                .setExpiration(new Date(new Date().getTime() + TOKEN_VALID_MILISECOND)) //JWT 만료 시간
                .signWith(key) //JWT 서명을 위한 키 설정
                .compact(); //JWT 문자열 생성 및 반환
    }
//    JWT 토큰에서 사용자 이름 추출
//    유효하지 않은 토큰일 경우 예외 발생
    public String getUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key) //서명 검증을 위한 키 설정
                .build()
                .parseClaimsJws(token) //토큰 파싱, jws는 서명이 있는 jwt로 파싱
                .getBody()
                .getSubject(); //Subject(사용자 이름) 반환
    }
//    JWT 검증 메소드 = 유효 기간 검사 및 서명 검증
    public boolean validateToken(String token) {
        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(key) //서명 검증을 위한 키 설정
                .build()
                .parseClaimsJws(token); //토큰 파싱 및 서명 검증
        return true; //유효한 토큰일 경우 true 반환
    }
}
