package com.ecommerce.orders.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateOrderRequest {
	@NotNull(message = "userId는 필수입니다.")
	private Long userId;
	@NotNull(message = "productId는 필수입니다.")
	private Long productId;
	@NotNull(message = "수량은 필수입니다.")
	@Min(value = 1, message = "최소 주문 수량은 1개 이상입니다.")
	private Long quantity;
}