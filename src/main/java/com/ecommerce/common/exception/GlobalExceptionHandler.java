package com.ecommerce.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ecommerce.common.response.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
		ErrorResponse errorResponse = new ErrorResponse(
			ex.getStatus(),
			ex.getMessage()
		);
		return ResponseEntity.status(ex.getStatus()).body(errorResponse);
	}
}
