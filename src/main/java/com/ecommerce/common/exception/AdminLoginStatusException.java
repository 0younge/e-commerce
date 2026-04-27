package com.ecommerce.common.exception;

import org.springframework.http.HttpStatus;

public class AdminLoginStatusException extends BusinessException {
	public AdminLoginStatusException() {
		super(HttpStatus.UNAUTHORIZED, "로그인이 필요한 작업입니다.");
	}
}
