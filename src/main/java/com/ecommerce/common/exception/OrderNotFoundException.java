package com.ecommerce.common.exception;

import org.springframework.http.HttpStatus;

public class OrderNotFoundException extends BusinessException {
	public OrderNotFoundException() {
		super(HttpStatus.NOT_FOUND, "주문을 찾을 수 없습니다.");
	}
}
