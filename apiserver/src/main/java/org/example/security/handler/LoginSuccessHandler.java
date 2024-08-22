package org.example.security.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.example.security.account.domain.CustomUser;
import org.example.security.account.dto.AuthResultDTO;
import org.example.security.account.dto.UserInfoDTO;
import org.example.security.util.JsonResponse;
import org.example.security.util.JwtProcessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Log4j
@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtProcessor jwtProcessor; //JWT 토큰 관련 메소드 처리

//    인증 결과로 AuthResultDTO 생성하는 메소드
    private AuthResultDTO makeAuthResult(CustomUser user){
        String username = user.getUsername();
//토큰 생성
        String token = jwtProcessor.generateToken(username);
// 토큰과 사용자 정보(MemberVO -> UserInfoDTO 변환)를 포함한 AuthResultDTO 생성 및 반환
        return new AuthResultDTO(token, UserInfoDTO.of(user.getMember()));
    }

//    인증 성공시 호출되는 메소드
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
       // 인증 결과에서 사용자 정보 가져옴
        CustomUser user = (CustomUser) authentication.getPrincipal();

// 인증 성공 결과를 생성하고 JSON으로 응답
        AuthResultDTO result = makeAuthResult(user);
        JsonResponse.send(response,result);
    }
}
