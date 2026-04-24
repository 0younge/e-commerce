package com.ecommerce.common.exception;

import org.springframework.http.HttpStatus;

/**
 * 중복 데이터 (회원가입, 상품 등록 등) 예시) 이미 존재하는 이메일입니다. 상품 등
 */
public class DuplicateResourceException extends BusinessException {

	public DuplicateResourceException(String message) {
		super(HttpStatus.CONFLICT, message);
	}
}
