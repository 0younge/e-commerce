package com.ecommerce.common.exception;

import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends BusinessException {

	public ProductNotFoundException() {
		super(HttpStatus.NOT_FOUND, "상품을 찾을 수 없습니다.");
	}
}
