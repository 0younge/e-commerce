package com.ecommerce.orders.dto;

import java.time.LocalDateTime;

import com.ecommerce.common.enums.OrderStatus;
import com.ecommerce.orders.entity.Order;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateOrderResponse {
	private final Long orderId;
	private final String number;
	private final Long userId;
	private final Long productId;
	private final Long adminId;
	private final Long quantity;
	private final Long totalPrice;
	private final OrderStatus status;
	private final LocalDateTime createdAt;

	public static CreateOrderResponse from(Order order, Long adminId) {
		return new CreateOrderResponse(
			order.getOrderId(),
			order.getNumber(),
			order.getUser().getUserId(),
			order.getProduct().getProductId(),
			adminId,
			order.getQuantity(),
			order.getTotalPrice(),
			order.getStatus(),
			order.getCreatedAt()
		);
	}
}