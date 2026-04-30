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

	// 매 정각마다 스켈줄링
	@Scheduled(cron = "0 0 * * * *")
	@Transactional
	public void deleteExpiredTokens() {
		// 현재 시간보다 이전에 만료된 토큰 전부 삭제
		blacklistedTokenRepository.deleteByExpiredAtBefore(LocalDateTime.now());
	}
}
