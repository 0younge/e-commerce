package com.ecommerce.users.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.ecommerce.common.enums.UserStatus;
import com.ecommerce.orders.entity.Order;
import com.ecommerce.users.entity.User;

import lombok.Getter;

@Getter
public class GetOneUserResponse {
	private final String name;
	private final String email;
	private final String phoneNumber;
	private final UserStatus status;
	private final Long orderCount;
	private final Long totalPrice;
	private final LocalDateTime createdAt;

	private GetOneUserResponse(String name, String email, String phoneNumber, UserStatus status, Long orderCount,
		Long totalPrice,
		LocalDateTime createdAt) {
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.status = status;
		this.orderCount = orderCount;
		this.totalPrice = totalPrice;
		this.createdAt = createdAt;
	}

	public static GetOneUserResponse from(User user) {
		List<Order> orders = user.getOrders();
		return new GetOneUserResponse(
			user.getName(),
			user.getEmail(),
			user.getPhoneNumber(),
			user.getStatus(),
			(long)orders.size(),
			orders.stream().mapToLong(Order::getTotalPrice).sum(),
			user.getCreatedAt()
		);
	}
}
