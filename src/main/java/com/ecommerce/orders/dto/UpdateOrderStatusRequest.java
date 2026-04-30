package com.ecommerce.orders.dto;

import com.ecommerce.common.enums.OrderStatus;

import lombok.Getter;

@Getter
public class UpdateOrderStatusRequest {
	private OrderStatus status;
}
