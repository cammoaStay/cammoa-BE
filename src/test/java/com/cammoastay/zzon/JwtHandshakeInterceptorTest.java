//package com.cammoastay.zzon;
//
//import com.cammoastay.zzon.chat.handlers.JwtHandshakeInterceptor;
//import com.cammoastay.zzon.login.jwt.JwtUtil;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.ServerHttpResponse;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.web.socket.WebSocketHandler;
//
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
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
//    private JwtUtil jwtUtil;  // 모킹할 JwtUtil 객체
//
//    @BeforeEach
//    void setUp() {
//        jwtUtil = mock(JwtUtil.class); // JwtUtil을 모킹
//        jwtHandshakeInterceptor = new JwtHandshakeInterceptor(jwtUtil); // 모킹된 JwtUtil을 주입
//    }
//
//    @Test
//    void testBeforeHandshake_NoAuthHeader() throws Exception {
//        // ServerHttpRequest 모킹
//        ServerHttpRequest request = mock(ServerHttpRequest.class);
//        ServerHttpResponse response = mock(ServerHttpResponse.class);
//        WebSocketHandler webSocketHandler = mock(WebSocketHandler.class);
//        Map<String, Object> attributes = new ConcurrentHashMap<>();
//
//        // Authorization 헤더 없음
//        when(request.getHeaders()).thenReturn(new HttpHeaders());
//
//        // beforeHandshake 호출
//        boolean result = jwtHandshakeInterceptor.beforeHandshake(request, response, webSocketHandler, attributes);
//
//        // 검증: 핸드셰이크 실패
//        assertFalse(result);
//        assertNull(attributes.get("username"));
//    }
//
//    @Test
//    void testBeforeHandshake_InvalidJwt() throws Exception {
//        // 잘못된 JWT 생성
//        String invalidJwtToken = "invalidToken";
//
//        // ServerHttpRequest 모킹
//        ServerHttpRequest request = mock(ServerHttpRequest.class);
//        ServerHttpResponse response = mock(ServerHttpResponse.class);
//        WebSocketHandler webSocketHandler = mock(WebSocketHandler.class);
//        Map<String, Object> attributes = new ConcurrentHashMap<>();
//
//        // Authorization 헤더 모킹
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + invalidJwtToken);
//        when(request.getHeaders()).thenReturn(headers);
//
//        // 모킹: 유효하지 않은 토큰으로 처리
//        when(jwtUtil.validToken(invalidJwtToken)).thenReturn(false);
//
//        // beforeHandshake 호출
//        boolean result = jwtHandshakeInterceptor.beforeHandshake(request, response, webSocketHandler, attributes);
//
//        // 검증: 핸드셰이크 실패
//        assertFalse(result);
//        assertNull(attributes.get("username"));
//    }
//
//    @Test
//    void testBeforeHandshake_ValidJwt() throws Exception {
//        // 유효한 JWT 생성
//        String validJwtToken = "validJwtToken";
//
//        // ServerHttpRequest 모킹
//        ServerHttpRequest request = mock(ServerHttpRequest.class);
//        ServerHttpResponse response = mock(ServerHttpResponse.class);
//        WebSocketHandler webSocketHandler = mock(WebSocketHandler.class);
//        Map<String, Object> attributes = new ConcurrentHashMap<>();
//
//        // Authorization 헤더 모킹
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + validJwtToken);
//        when(request.getHeaders()).thenReturn(headers);
//
//        // 모킹: 유효한 토큰으로 처리
//        when(jwtUtil.validToken(validJwtToken)).thenReturn(true);
//        when(jwtUtil.getUsername(validJwtToken)).thenReturn("testUser");
//        when(jwtUtil.getRole(validJwtToken)).thenReturn("ROLE_USER");
//
//        // beforeHandshake 호출
//        boolean result = jwtHandshakeInterceptor.beforeHandshake(request, response, webSocketHandler, attributes);
//
//        // 검증: 핸드셰이크 성공 및 사용자 정보 저장
//        assertTrue(result);
//        assertEquals("testUser", attributes.get("username"));
//        assertEquals("ROLE_USER", attributes.get("role"));
//    }
//}
