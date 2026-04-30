package com.ecommerce.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ecommerce.common.security.handler.CustomAccessDeniedHandler;
import com.ecommerce.common.security.handler.CustomAuthenticationEntryPoint;
import com.ecommerce.common.security.jwt.JwtAuthenticationFilter;

import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor

public class SecurityConfig {
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
	private final CustomAccessDeniedHandler customAccessDeniedHandler;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			// 1. JWT 기반 REST API에서는 CSRF 방어를 사용하지 않음
			.csrf(csrf -> csrf.disable())

			.exceptionHandling(exception -> exception
				.authenticationEntryPoint(customAuthenticationEntryPoint)
				.accessDeniedHandler(customAccessDeniedHandler)
			)

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

				.dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()

				.requestMatchers(
					"/admins/signup",
					"/admins/login",
					"/health"
				).permitAll()

				// 관리자 도메인: 모든 관리자
				.requestMatchers("/admins/**").hasAnyRole("SUPER_ADMIN", "OPERATION_ADMIN", "CS_ADMIN")

				// 상품 조회는 모두 허용
				.requestMatchers(HttpMethod.GET,"/products/**").permitAll()

				// 상품 도메인 : 슈퍼관리자 및 운영관리자만
				.requestMatchers("/products/**").hasAnyRole("SUPER_ADMIN", "OPERATION_ADMIN")

				// 주문 생성 : 모두 허용
				.requestMatchers(HttpMethod.POST, "/orders").permitAll()

				// 주문 도메인 : 모든 관리자
				.requestMatchers("/orders/**").hasAnyRole("SUPER_ADMIN", "OPERATION_ADMIN", "CS_ADMIN")

				// 고객 삭제 : 슈퍼 관리자만
				.requestMatchers(HttpMethod.DELETE, "/users/**").hasRole("SUPER_ADMIN")

				// 리뷰 삭제 : 슈퍼관리자 및 운영관리자만
				.requestMatchers(HttpMethod.DELETE, "/reviews/**").hasAnyRole("SUPER_ADMIN", "OPERATION_ADMIN")

				// 나머지 요청은 전부 인증 필요
				.anyRequest().authenticated()
			)
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
