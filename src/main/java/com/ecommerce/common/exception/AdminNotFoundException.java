package com.ecommerce.common.exception;

import org.springframework.http.HttpStatus;

public class AdminNotFoundException extends BusinessException {

	public AdminNotFoundException(String message) {
		super(HttpStatus.NOT_FOUND, message);
	}
}
