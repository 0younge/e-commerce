package com.ecommerce.dashboard.dto;

import com.ecommerce.common.enums.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RecentOrderDto {
	private final Long orderId;
	private final String userName;
	private final String productName;
	private final Long price;
	private final OrderStatus status;
}
