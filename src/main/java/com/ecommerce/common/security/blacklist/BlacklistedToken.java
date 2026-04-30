package com.ecommerce.common.security.blacklist;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "blacklisted_tokens")
public class BlacklistedToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// TODO: 토큰 길이가 길어질 수 있어 length를 수정
	@Column(nullable = false, unique = true, length = 1000)
	private String token;

	@Column(nullable = false)
	private LocalDateTime expiredAt;

	protected BlacklistedToken() {
	}

	public BlacklistedToken(String token, LocalDateTime expiredAt) {
		this.token = token;
		this.expiredAt = expiredAt;
	}
}
