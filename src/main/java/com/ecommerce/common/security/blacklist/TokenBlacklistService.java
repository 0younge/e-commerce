package com.ecommerce.common.security.blacklist;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.common.security.jwt.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenBlacklistService {

	private final BlacklistedTokenRepository blacklistedTokenRepository;
	private final JwtTokenProvider jwtTokenProvider;

	/**
	 * 블랙리스트 등록
	 * @param token
	 */
	@Transactional
	public void addToBlacklist(String token) {
		if (blacklistedTokenRepository.existsByToken(token)) {
			return;
		}

		/*DB에서 언제 삭제할지 판단하기 위해 exp 값 추출*/
		LocalDateTime expiredAt = jwtTokenProvider.getExpiration(token);

		BlacklistedToken blacklistedToken =
			new BlacklistedToken(token, expiredAt);

		blacklistedTokenRepository.save(blacklistedToken);
	}

	/**
	 * 블랙리스트인지 확인
	 * @param token
	 * @return
	 */
	@Transactional(readOnly = true)
	public boolean isBlacklisted(String token) {
		return blacklistedTokenRepository.existsByToken(token);
	}
}
