package com.ecommerce.orders.dto;

import com.ecommerce.common.enums.OrderStatus;
import com.ecommerce.orders.entity.Order;

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

	public static GetOrderAllResponse from(Order order) {
		return new GetOrderAllResponse(
			order.getOrderId(),
			order.getNumber(),
			order.getUser().getName(),
			order.getProduct().getName(),
			order.getQuantity(),
			order.getTotalPrice(),
			order.getStatus(),
			order.getAdmin() != null ? order.getAdmin().getName() : null
		);
	}

}
