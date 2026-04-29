package com.ecommerce.common.security.blacklist;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BlacklistCleanupScheduler {
	private final BlacklistedTokenRepository blacklistedTokenRepository;

	@Scheduled(cron = "0 0 * * * *")
	@Transactional
	public void deleteExpiredTokens() {
		blacklistedTokenRepository.deleteByExpiredAtBefore(LocalDateTime.now());
	}
}
