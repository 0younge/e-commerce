package com.ecommerce.orders.dto;

import java.time.LocalDateTime;

import com.ecommerce.admins.entity.AdminRole;
import com.ecommerce.common.enums.OrderStatus;
import com.ecommerce.orders.entity.Order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GetOrderOneResponse {
	private final String number;
	private final String userName;
	private final String userEmail;
	private final String productName;
	private final Long quantity;
	private final Long totalPrice;
	private final LocalDateTime createdAt;
	private final OrderStatus status;
	private final String adminName;
	private final String adminEmail;
	private final AdminRole adminRole;

	public static GetOrderOneResponse from(Order order) {
		return new GetOrderOneResponse(
			order.getNumber(),
			order.getUser().getName(),
			order.getUser().getEmail(),
			order.getProduct().getName(),
			order.getQuantity(),
			order.getTotalPrice(),
			order.getCreatedAt(),
			order.getStatus(),
			order.getAdmin() != null ? order.getAdmin().getName() : null,
			order.getAdmin() != null ? order.getAdmin().getEmail() : null,
			order.getAdmin() != null ? order.getAdmin().getRole() : null
		);
	}
}
