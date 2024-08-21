package org.example.security.util;

import lombok.extern.log4j.Log4j;
import org.example.config.RootConfig;
import org.example.security.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RootConfig.class, SecurityConfig.class})
@Log4j
class JwtProcessorTest {

    @Autowired
    JwtProcessor jwtProcessor;

    @Test
    void generateToken() {
//        JWT 토큰 생성 테스트
        String username = "user0";
        String token = jwtProcessor.generateToken(username);
        log.info(token);
        assertNotNull(token); //토큰이 null이 아닌지 확인하여 테스트 검증
    }

    @Test
    void getUsername() {
//        JWT 토큰에서 사용자 이름 추출하는 테스트
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMCIsImlhdCI6MTcyNDIyNDg3OSwiZXhwIjoxNzI0MjI1MTc5fQ.PxbfFi8QxB4uL-QM0vOaibImnE_xQ8ABwQ44DtaPXfOzQJbuOkrC6gw1dSZKO944qWmpGzVu1YH3l59ad5gr-A";

        String username = jwtProcessor.getUsername(token); //JWT 토큰에서 사용자 이름 추출
        log.info(username);
        assertNotNull(username); //사용자 이름이 null이 아닌지 확인
    }

    @Test
    void validateToken() {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMCIsImlhdCI6MTcyNDIyNDg3OSwiZXhwIjoxNzI0MjI1MTc5fQ.PxbfFi8QxB4uL-QM0vOaibImnE_xQ8ABwQ44DtaPXfOzQJbuOkrC6gw1dSZKO944qWmpGzVu1YH3l59ad5gr-A";

        boolean isValid = jwtProcessor.validateToken(token); //토큰 유효성 검사
        log.info(isValid);
        assertTrue(isValid); //유효성 여부 확인
    }
}