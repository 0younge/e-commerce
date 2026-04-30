package com.ecommerce.common.enums;

import java.util.Arrays;

import com.ecommerce.common.exception.InvalidRequestException;
import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * 주문 상태: 준비중, 배송중, 배송완료, 취소됨
 */
public enum OrderStatus {
	READY,
	SHIPPING,
	DELIVERED,
	CANCELED;

	@JsonCreator
	public static OrderStatus from(String value) {
		return Arrays.stream(values())
			.filter(v -> v.name().equalsIgnoreCase(value))
			.findFirst()
			.orElseThrow(() -> new InvalidRequestException("잘못된 상태값입니다."));
	}
}
