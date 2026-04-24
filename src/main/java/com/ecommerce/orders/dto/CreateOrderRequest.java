package com.ecommerce.orders.dto;

import lombok.Getter;

@Getter
public class CreateOrderRequest {
	private Long userId;
	private Long productId;
	private Long quantity;
}