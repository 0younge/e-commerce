package com.ecommerce.common.security.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ecommerce.admins.entity.AdminRole;

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
}
