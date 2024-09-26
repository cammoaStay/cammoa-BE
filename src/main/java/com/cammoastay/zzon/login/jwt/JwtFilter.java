	package com.cammoastay.zzon.login.jwt;

	import com.cammoastay.zzon.jointest.MemberEntity;
	import com.cammoastay.zzon.login.dto.CustomUserDetails;
	import jakarta.servlet.FilterChain;
	import jakarta.servlet.ServletException;
	import jakarta.servlet.http.HttpServletRequest;
	import jakarta.servlet.http.HttpServletResponse;
	import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
	import org.springframework.security.core.Authentication;
	import org.springframework.security.core.context.SecurityContextHolder;
	import org.springframework.web.filter.OncePerRequestFilter;

	import java.io.IOException;
	
	
	// JWT 토큰을 처리하여 JWT 검증을 수행하는 필터 클래스
	public class JwtFilter extends OncePerRequestFilter {
		
		private final JwtUtil jwtUtil;
		public JwtFilter(JwtUtil jwtUtil) {
			this.jwtUtil = jwtUtil;
        }
		
		
		/* 
		 * OncePerRequestFilter 상속받아 dofilterInternal 메서드를 오버라이딩하여
		   JWT로 커스텀 진행
		 */
		@Override
		protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
				throws ServletException, IOException {
			String authorization = request.getHeader("Authorization");

			if (authorization == null || !authorization.startsWith("Bearer ")) {
				filterChain.doFilter(request, response);
				return;
			}

			String token = authorization.split(" ")[1];
			if (jwtUtil.isExpired(token)) {
				filterChain.doFilter(request, response);
				return;
			}
			MemberEntity userEntity = new MemberEntity();

			CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);
			Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authToken);
			// 로그 출력
			logger.info("SecurityContextHolder에 설정된 인증 정보: " + SecurityContextHolder.getContext().getAuthentication());

			// 필터 체인 실행
			filterChain.doFilter(request, response);
//			String authorization = request.getHeader("Authorization");
//
//			if (authorization == null || !authorization.startsWith("Bearer " )) {
//				// 토큰이 존재하지 않거나 Bearer 토큰이 아닌 경우
//				filterChain.doFilter(request, response); // 다음 필터로 요청 전달
//				// 조건이 해당되면 메소드 종료(필수)
//				return;
//			}
//
//			String token = authorization.split(" ")[1];
//			// 토큰 소멸시간 검증
//			if (jwtUtil.isExpired(token)) {
//				// 토큰이 만료된 경우
//				filterChain.doFilter(request, response); // 다음 필터로 요청 전달
//				return;
//			}
//
//
//
//
//			// 토큰에서 사용자 정보 추출
//			UserEntity userEntity = new UserEntity();
//			// 사용자 정보를 기반으로 UserDetails 객체 생성
//			CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);
//			 // Authentication 객체 생성
//			Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails,  null, customUserDetails.getAuthorities());
//			// SecurityContextHolder에 Authentication 객체 설정
//			SecurityContextHolder.getContext().setAuthentication(authToken);
//			filterChain.doFilter(request, response); // 다음 필터로 요청 전달
//
//			Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails,  null, customUserDetails.getAuthorities());;
//			SecurityContext context = SecurityContextHolder.createEmptyContext();
//			context.setAuthentication(authentication);
//			SecurityContextHolder.setContext(context);
		}
	}
