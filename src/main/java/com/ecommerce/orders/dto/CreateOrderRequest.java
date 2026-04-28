package com.ecommerce.orders.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;

@Getter
public class CreateOrderRequest {
	@NotNull
	private Long userId;
	@NotNull
	private Long productId;
	@NotNull
	@PositiveOrZero
	private Long quantity;
}