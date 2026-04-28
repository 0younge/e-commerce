package com.ecommerce.common.security.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ecommerce.admins.entity.AdminRole;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

	private final SecretKey secretKey;
	private final long expiration;

	public JwtTokenProvider(
		@Value("${jwt.secret}") String secret,
		@Value("${jwt.expiration}") long expiration
	) {
		this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
		this.expiration = expiration;
	}

	public String createToken(Long adminId, String email, AdminRole role) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + expiration);

		return Jwts.builder()
			.subject(String.valueOf(adminId))
			.claim("email", email)
			.claim("role", role.name())
			.issuedAt(now)
			.expiration(expiryDate)
			.signWith(secretKey)
			.compact();
	}

	/*JWT 토큰 해석 및 검증 클래스 구현 */

	/**
	 * 토큰 정상 유무 검증
	 * @param token
	 * @return
	 */
	public boolean validateToken(String token) {
		try {
			/* 서명 검증, 만료체크, 토큰 형식 체크*/
			getClaims(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/* getXXX 토큰에서 사용자 정보 추출 */
	public Long getAdminId(String token) {
		return Long.valueOf(getClaims(token).getSubject());
	}

	public String getEmail(String token) {
		return getClaims(token).get("email", String.class);
	}

	public String getRole(String token) {
		return getClaims(token).get("role", String.class);
	}

	private Claims getClaims(String token) {
		return Jwts.parser() // JWT 파싱 시작
			.verifyWith(secretKey) // 서명 검증
			.build()  //파서 객체 생성
			.parseSignedClaims(token) // 실제 검증 + 파싱 수행
			.getPayload();  // 반환
	}
}
