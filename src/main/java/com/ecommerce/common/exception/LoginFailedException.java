package com.ecommerce.common.exception;

import org.springframework.http.HttpStatus;

public class LoginFailedException extends BusinessException {

	public LoginFailedException() {
		super(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 올바르지 않습니다.");
	}
}
