package com.cammoastay.zzon.login.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

//JWT 토큰을 생성하고 검증하는 유틸리티 클래스
@Component
public class JwtUtil {
	
	private final SecretKey secretKey;

	// 생성자: yml 파일에서 비밀 키를 읽어와서 SecretKey 객체를 생성함
	public JwtUtil(@Value("${jwt.secret}") String secret) {
		this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
	}
	
	public String getUsername(String token) {
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
	}
	
	public String getRole(String token) {
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
	}
	
	public String getCategory(String token) {
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("category", String.class);
	}
	
	public Boolean isExpired(String token) {
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
	}
	
	public String createJwt(String category, String userId, String role, long expiredMs) {

		// JWT 토큰 생성하는 메서드
		return Jwts.builder()
	            .claim("category", category) // 페이로드에 카테고리 추가
	            .claim("username", userId) // 페이로드에 사용자 이름 추가
	            .claim("role", role) // 페이로드에 역할 추가
	            .issuedAt(new Date(System.currentTimeMillis())) // 토큰 발급 시간 설정
	            .expiration(new Date(System.currentTimeMillis() + expiredMs)) // 토큰 만료 시간 설정
	            .signWith(secretKey)  // 비밀 키로 서명
	            .compact(); // 최종적으로 JWT 문자열 생성
	}

	public boolean validToken(String token) {
		try {
			parseToken(token);
			return true;
		} catch (Exception e) {
			// 예외 처리 (필요에 따라 추가)
			return false;
		}
	}

	private Claims parseToken(String token) {
		return Jwts.parser()
				.verifyWith(secretKey)
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
}
