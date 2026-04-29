package com.ecommerce.common.security.jwt;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ecommerce.admins.entity.AdminRole;
import com.ecommerce.common.security.auth.SecurityAdminInfo;
import com.ecommerce.common.security.blacklist.TokenBlacklistService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;
	private final TokenBlacklistService tokenBlacklistService;

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
			SecurityAdminInfo adminInfo = new SecurityAdminInfo(
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