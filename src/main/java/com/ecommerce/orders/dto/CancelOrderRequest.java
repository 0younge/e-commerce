package com.ecommerce.orders.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CancelOrderRequest {
	@NotBlank(message = "취소 사요는 필수로 작성해야 합니다..")
	private String cancelReason;
}
