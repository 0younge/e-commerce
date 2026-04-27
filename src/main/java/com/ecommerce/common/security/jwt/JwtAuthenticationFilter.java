package com.ecommerce.common.security.jwt;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ecommerce.admins.entity.AdminInfo; // 수정: JWT 인증 후 principal로 담을 관리자 정보
import com.ecommerce.admins.entity.AdminRole; // 수정: 문자열 role을 AdminRole enum으로 변환하기 위해 추가

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;

	@Override
	protected void doFilterInternal(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain
	) throws ServletException, IOException {

		String token = resolveToken(request);

		if (token != null && jwtTokenProvider.validateToken(token)) {
			Long adminId = jwtTokenProvider.getAdminId(token);
			String email = jwtTokenProvider.getEmail(token);
			String role = jwtTokenProvider.getRole(token);

			SimpleGrantedAuthority authority =
				new SimpleGrantedAuthority("ROLE_" + role);

			// 수정: 컨트롤러에서 @AuthenticationPrincipal로 꺼내 쓸 AdminInfo 생성
			AdminInfo adminInfo = new AdminInfo(
				adminId,
				email,
				AdminRole.valueOf(role)
			);

			UsernamePasswordAuthenticationToken authentication =
				new UsernamePasswordAuthenticationToken(
					adminInfo, // 수정: 기존 email 대신 AdminInfo를 principal로 저장
					null,
					List.of(authority)
				);

			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		filterChain.doFilter(request, response);
	}

	private String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");

		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}

		return null;
	}
}