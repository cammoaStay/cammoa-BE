package com.cammoastay.zzon.chat.handlers;

import com.cammoastay.zzon.login.jwt.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
public class JwtHandshakeInterceptor implements ChannelInterceptor {

    private final JwtUtil jwtUtil;

    public JwtHandshakeInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }


    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        // 핸드쉐이크 이후 연결되면 가공된 헤더를 추출할수가없다 그래서 인터셉트하여
        // 연결되기전에 NativeHeader에서 토큰을 추출, userid를 검증하여
        // Stomp 컨트롤러에 세션객체로 보내 Stomp에서 username으로 멤버로찾아 진행

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        System.out.println("message:" + message);
        System.out.println("헤더 : " + message.getHeaders());
        System.out.println("토큰" + accessor.getNativeHeader("Authorization"));
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            jwtUtil.validToken(Objects.requireNonNull(accessor.getFirstNativeHeader("Authorization")).substring(7));
        }

        // CONNECT 메시지 처리 시 Authorization 헤더에서 토큰 추출
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {

            // nativeHeaders를 추출하여 Authorization 헤더 값 가져오기
            Map<String, List<String>> nativeHeaders = accessor.getMessageHeaders().get("nativeHeaders", Map.class);

            if (nativeHeaders != null) {
                List<String> authorizationHeader = nativeHeaders.get("Authorization");

                if (authorizationHeader != null && !authorizationHeader.isEmpty()) {
                    String token = authorizationHeader.get(0).replace("Bearer ", "").trim();

                    // JWT 검증 및 사용자 정보 추출
                    String username = jwtUtil.getUsername(token);
                    String role = jwtUtil.getRole(token);
                    log.info("Role from JWT: {}", role);

                    // 세션에 사용자 정보 저장
                    accessor.getSessionAttributes().put("username", username);
                }
            }
        }
        return message;
    }



}
