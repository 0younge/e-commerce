package com.ecommerce.orders.dto;

import com.ecommerce.common.enums.OrderStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GetOrderListResponse {
	private final Long id;
	private final String Number;
	private final Long userId;
	private final Long productId;
	private final Long quantity;
	private final Long totalPrice;
	private final OrderStatus status;
	private final Long adminId;
}
