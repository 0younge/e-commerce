package com.ecommerce.common.exception;

import org.springframework.http.HttpStatus;

/**
 * 비즈니스 규칙 위반 (DTO 검증 말고, 로직 검증) 예시) 주문 수량은 1개 이상이어야 합니다.
 */
public class InvalidRequestException extends BusinessException {

	public InvalidRequestException(String message) {
		super(HttpStatus.BAD_REQUEST, message);
	}
}
