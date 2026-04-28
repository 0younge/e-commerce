package com.ecommerce.common.exception;

import org.springframework.http.HttpStatus;

public class ReviewNotFoundException extends BusinessException {
	public ReviewNotFoundException() {super(HttpStatus.NOT_FOUND, "리뷰를 찾을 수 없습니다.");}
}







