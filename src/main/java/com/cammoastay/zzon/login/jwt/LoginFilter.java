package com.cammoastay.zzon.login.jwt;

import com.cammoastay.zzon.login.entity.RefreshEntity;
import com.cammoastay.zzon.login.repository.RefreshRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;


//로그인을 처리하는 필터 클래스
public class LoginFilter extends UsernamePasswordAuthenticationFilter { 

	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;
	private final RefreshRepository refreshRepository;
	
	public LoginFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil, RefreshRepository refreshRepository) 
	{		
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
		this.refreshRepository = refreshRepository;
		setFilterProcessesUrl("/api/login"); // 로그인 URL 설정
		
		

	}
	// 실제 로그인 시도를 처리하는 메서드
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		
		 try {
		        // 요청에서 JSON 데이터를 파싱하여 username과 password 추출
		        ObjectMapper objectMapper = new ObjectMapper();
		        JsonNode jsonNode = objectMapper.readTree(request.getInputStream());
		        String username = jsonNode.get("userId").textValue();
		        String password = jsonNode.get("userPw").textValue();
		        


		        // 스프링 시큐리티에서 username과 password를 검증하기 위한 UsernamePasswordAuthenticationToken 생성
		        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);
		        // 생성된 토큰을 AuthenticationManager로 전달하여 인증 시도

		        return authenticationManager.authenticate(authToken);
		       
		    } catch (IOException e) {
		        throw new RuntimeException("Could not read JSON request", e);
		    }
	}
	
		@Override
		protected void successfulAuthentication( HttpServletRequest request, HttpServletResponse response,
				FilterChain chain, Authentication authentication )
		{
		    //유저 정보

		    String username = authentication.getName();
		    
		    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		    Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
		    GrantedAuthority auth = iterator.next();
		    String role = auth.getAuthority();

		    String access = jwtUtil.createJwt("access", username, role, 600000L);
		    String refresh = jwtUtil.createJwt("refresh", username, role, 86400000L);
		    addRefreshEntity(username, refresh, 86400000L);
		    response.setHeader("access", access);
		    response.addHeader("Authorization", access);
		    response.addCookie(createCookie("refresh", refresh) ); // 수정
		    response.addHeader("refresh", access);
		    response.setStatus(HttpStatus.OK.value());
		    

		}
		
		 // 로그인 실패 시 실행되는 메서드
		@Override
		protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse reponse,  AuthenticationException failed )
		{	
			reponse.setStatus(401);
			// 로그인 실패시 에러.
		}
		
		 // 리프레시 토큰 엔티티를 추가하는 메서드
		private void addRefreshEntity(String username, String refresh, Long expiredMs) {
		
			Date date = new Date(System.currentTimeMillis() + expiredMs);		
			RefreshEntity refreshEntity = new RefreshEntity();
			refreshEntity.setUsername(username);
			refreshEntity.setRefresh(refresh);
			refreshEntity.setExpiration(date.toString());	
			refreshRepository.save(refreshEntity);
		}
		
		// 쿠키 생성 메서드
		private Cookie createCookie(String key, String value ) { 
			Cookie cookie = new Cookie(key, value);
		    cookie.setMaxAge(24*60*60); // 쿠키의 유효 시간 설정 (24시간)
		    cookie.setSecure(true); //보안 설정 (HTTPS 연결에서만 전송)
		    cookie.setPath("/"); // 쿠키의 유효 경로 설정 (전체 경로에 대해 유효)
		    cookie.setAttribute("SameSite", "None"); // SameSite 속성 설정 (Cross-Site 요청 방지)
		    cookie.setHttpOnly(true); // JavaScript로 쿠키에 접근 불가능하게 설정
		   
		    

		    return cookie;
		}	
}
