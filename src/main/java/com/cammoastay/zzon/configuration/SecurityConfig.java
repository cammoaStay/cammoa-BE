package com.cammoastay.zzon.configuration;

import com.cammoastay.zzon.login.jwt.CustomLogoutFilter;
import com.cammoastay.zzon.login.jwt.JwtFilter;
import com.cammoastay.zzon.login.jwt.JwtUtil;
import com.cammoastay.zzon.login.jwt.LoginFilter;
import com.cammoastay.zzon.login.repository.RefreshRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	 private final AuthenticationConfiguration authenticationConfiguration;
	    private final JwtUtil jwtUtil;
	    private final RefreshRepository refreshRepository;

	    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JwtUtil jwtUtil, RefreshRepository refreshRepository
	    		) {

	        this.authenticationConfiguration = authenticationConfiguration;
	        this.jwtUtil = jwtUtil;
	        this.refreshRepository = refreshRepository;

	    }

	    @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
	        return configuration.getAuthenticationManager();
	    }
	    @Bean
	    public BCryptPasswordEncoder bCryptPasswordEncoder() {

	        return new BCryptPasswordEncoder();
	    }
	    // SecurityFilterChain을 구성하는 메서드입니다. CORS 설정을 정의합니다.

	    @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
			http
					.cors(corsCustomizer -> corsCustomizer.configurationSource(request -> {
						CorsConfiguration configuration = new CorsConfiguration();
						configuration.setAllowedOrigins(Collections.singletonList("http://localhost:5173")); // 허용할 도메인 명시
						configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // 필요한 메서드만 명시
						configuration.setAllowCredentials(true);
						configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Current-Chatroom-Id")); // 필요한 헤더만 허용
						configuration.setMaxAge(3600L);
						configuration.addExposedHeader("Authorization");
						configuration.addExposedHeader("Set-Cookie");
						return configuration;
					}))
					.csrf(csrf -> csrf.disable())  // CSRF 비활성화
					.formLogin(auth -> auth.disable())  // Form Login 비활성화
					.httpBasic(auth -> auth.disable())  // HTTP 기본 인증 비활성화
					.authorizeHttpRequests(auth -> auth
							.requestMatchers("/chats/**","/**").permitAll())
						 // 나머지 경로는 인증 불필요
					.sessionManagement(session -> session
							.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
					// 상태 없는 세션 사용
					.addFilterBefore(new JwtFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)  // JwtFilter 추가
					.addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil, refreshRepository), UsernamePasswordAuthenticationFilter.class)
					.addFilterBefore(new CustomLogoutFilter(jwtUtil, refreshRepository), LogoutFilter.class);  // Logout 필터 추가

			return http.build();
	    }


}





