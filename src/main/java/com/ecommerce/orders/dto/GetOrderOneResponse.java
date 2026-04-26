package com.ecommerce.orders.dto;

import java.time.LocalDateTime;

import com.ecommerce.admins.entity.AdminRole;
import com.ecommerce.common.enums.OrderStatus;

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
	private final AdminRole adminRle;
}
