package com.ecommerce.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
/**
 * 공통 부모 커스텀 에러
 */

public class BusinessException extends RuntimeException {
	private final HttpStatus status;

	public BusinessException(HttpStatus status, String message) {
		super(message);
		this.status = status;
	}
}
