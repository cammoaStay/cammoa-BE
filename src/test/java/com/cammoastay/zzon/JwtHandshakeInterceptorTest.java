//package com.cammoastay.zzon;
//
//import com.cammoastay.zzon.chat.handlers.JwtHandshakeInterceptor;
//import com.cammoastay.zzon.login.jwt.JwtUtil;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.MessageChannel;
//import org.springframework.messaging.simp.stomp.StompCommand;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.messaging.support.GenericMessage;
//import org.springframework.test.context.TestPropertySource;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//@TestPropertySource(properties = {
//        "jwt.secret=36115f098c6ee700e838c927205161eec6095683f2036d8981316e8739f4696b79b29bc1c882a75e13f3bc104a4e487ad320b107c4a03f61d5862e26cff9d31a"
//})
//class JwtHandshakeInterceptorTest {
//
//    private JwtHandshakeInterceptor jwtHandshakeInterceptor;
//
//    @Mock
//    private JwtUtil jwtUtil;  // 모킹할 JwtUtil 객체
//
//    @BeforeEach
//    void setUp() {
//        jwtUtil = mock(JwtUtil.class); // JwtUtil을 모킹
//        jwtHandshakeInterceptor = new JwtHandshakeInterceptor(jwtUtil); // 모킹된 JwtUtil을 주입
//    }
//
//    @Test
//    void testPreSend_NoAuthHeader() {
//        // Message 객체를 모킹하여 헤더 없이 처리
//        StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.CONNECT);
//        Map<String, Object> headers = new HashMap<>();
//        Message<Object> message = new GenericMessage<>(null, headers);
//
//        // preSend 메서드 호출
//        Message<?> result = jwtHandshakeInterceptor.preSend(message, mock(MessageChannel.class));
//
//        // 검증: Authorization 헤더가 없으므로 예외가 발생하지 않음
//        assertNotNull(result);
//    }
//
//    @Test
//    void testPreSend_InvalidJwt() {
//        // Invalid JWT 설정
//        String invalidJwtToken = "invalidToken";
//
//        // Message 객체 모킹
//        StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.CONNECT);
//        accessor.addNativeHeader("Authorization", "Bearer " + invalidJwtToken);
//        Map<String, List<String>> headers = accessor.toNativeHeaderMap();
//        Message<Object> message = new GenericMessage<>(null, headers);
//
//        // JWT가 유효하지 않다고 모킹
//        when(jwtUtil.validToken(invalidJwtToken)).thenThrow(new IllegalArgumentException("Invalid token"));
//
//        // preSend 메서드 호출 및 검증
//        assertThrows(IllegalArgumentException.class, () -> jwtHandshakeInterceptor.preSend(message, mock(MessageChannel.class)));
//    }
//
//    @Test
//    void testPreSend_ValidJwt() {
//        // Valid JWT 설정
//        String validJwtToken = "validJwtToken";
//
//        // Message 객체 모킹
//        StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.CONNECT);
//        accessor.addNativeHeader("Authorization", "Bearer " + validJwtToken);
//        Map<String, List<String>> headers = accessor.toNativeHeaderMap();
//        Message<Object> message = new GenericMessage<>(null, headers);
//
//        // 모킹: 유효한 토큰으로 처리
//        when(jwtUtil.validToken(validJwtToken)).thenReturn(true);
//        when(jwtUtil.getUsername(validJwtToken)).thenReturn("testUser");
//        when(jwtUtil.getRole(validJwtToken)).thenReturn("ROLE_USER");
//
//        // preSend 메서드 호출
//        Message<?> result = jwtHandshakeInterceptor.preSend(message, mock(MessageChannel.class));
//
//        // 검증: JWT 검증이 통과하고 사용자 정보가 세션에 저장되는지 확인
//        assertNotNull(result);
//        assertEquals("testUser", accessor.getSessionAttributes().get("username"));
//        assertEquals("ROLE_USER", accessor.getSessionAttributes().get("role"));
//    }
//}
