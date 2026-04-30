package com.ecommerce.common.security.blacklist;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BlacklistedTokenRepository extends JpaRepository<BlacklistedToken, Long> {

	// 요청마다 이 토큰이 블랙리스트인지 확인
	boolean existsByToken(String token);

	// 만료된 토큰 삭제
	void deleteByExpiredAtBefore(LocalDateTime now);
}
