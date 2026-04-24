package com.ecommerce.common.exception;

import org.springframework.http.HttpStatus;

public class AccessDeniedException extends BusinessException {

	public AccessDeniedException() {
		super(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
	}
}
