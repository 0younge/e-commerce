package com.ecommerce.common.security.blacklist;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BlacklistedTokenRepository extends JpaRepository<BlacklistedToken, Long> {

	boolean existsByToken(String token);

	void deleteByExpiredAtBefore(LocalDateTime now);
}
