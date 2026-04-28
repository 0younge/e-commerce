package com.ecommerce.common.exception;

import org.springframework.http.HttpStatus;

public class AdminStatusException extends BusinessException {

	public AdminStatusException(HttpStatus status, String message) {
		super(status, message);
	}
}
