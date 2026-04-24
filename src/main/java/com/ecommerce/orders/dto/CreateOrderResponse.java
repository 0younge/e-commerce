package com.ecommerce.orders.dto;

import java.time.LocalDateTime;

import com.ecommerce.common.enums.OrderStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateOrderResponse {
	private final Long orderId;
	private final String number;
	private final Long userId;
	private final Long productId;
	private final Long adminId;
	private final Long quantity;
	private final OrderStatus status;
	private final Long totalPrice;
	private final LocalDateTime createdAt;
}