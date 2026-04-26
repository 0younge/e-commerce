package com.ecommerce.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity

public class SecurityConfig {
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			// 1. JWT 기반 REST API에서는 CSRF 방어를 사용하지 않음
			.csrf(csrf -> csrf.disable())

			// 2. 세션 로그인 사용 안 함
			.sessionManagement(session ->
				session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			)

			// 3. formLogin 비활성화
			.formLogin(form -> form.disable())

			// 4. httpBasic 비활성화
			.httpBasic(basic -> basic.disable())

			// 5. API별 접근 정책
			.authorizeHttpRequests(auth -> auth
				// 공개 API
				.requestMatchers(
					"/api/admins/signup",
					"/api/admins/login",
					"/api/health"
				).permitAll()

				// 관리자 도메인: 일단 로그인한 관리자만
				.requestMatchers("/api/admins/**").authenticated()

				// 상품 도메인: 일단 로그인한 관리자만
				.requestMatchers("/api/products/**").authenticated()

				// 주문 도메인: 일단 로그인한 관리자만
				.requestMatchers("/api/orders/**").authenticated()

				// 고객 도메인: 일단 로그인한 관리자만
				.requestMatchers("/api/customers/**").authenticated()

				// 나머지 요청은 전부 인증 필요
				.anyRequest().authenticated()
			);

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
