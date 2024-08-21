package org.example.security.account.mapper;

import org.example.security.account.domain.MemberVO;

public interface UserDetailsMapper {
//    username으로 조회해서 사용자의 정보를 가져오는 메소드
    public MemberVO get(String username);
}
