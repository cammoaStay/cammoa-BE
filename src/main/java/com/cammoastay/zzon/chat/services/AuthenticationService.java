package com.cammoastay.zzon.chat.services;

import com.cammoastay.zzon.jointest.MemberEntity;
import com.cammoastay.zzon.jointest.MemberService;
import com.cammoastay.zzon.login.jwt.JwtUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    private final JwtUtil jwtUtil;
    private final MemberService memberService;

    public AuthenticationService(JwtUtil jwtUtil, MemberService memberService) {
        this.jwtUtil = jwtUtil;
        this.memberService = memberService;
    }
    public Optional<MemberEntity> memberFindByToken(String token) {
        String parseToken = token.substring("Bearer ".length()).trim();
        String username = jwtUtil.getUsername(parseToken); // JWT에서 username 추출
        // 사용자 정보를 이용해 MemberEntity 조회 (DB 또는 서비스에서 조회)
        return memberService.getUsername(username);
    }

}
