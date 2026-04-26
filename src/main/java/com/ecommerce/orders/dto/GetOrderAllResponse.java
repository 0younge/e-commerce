package com.ecommerce.orders.dto;

import com.ecommerce.common.enums.OrderStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GetOrderAllResponse {
	private final Long orderId;
	private final String number;
	private final String userName;
	private final String productName;
	private final Long quantity;
	private final Long totalPrice;
	private final OrderStatus status;
	private final String adminName;
}
