package com.ecommerce.products.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateQuantityRequest {

	@NotNull(message = "관리자 ID는 필수입니다.")
	private Long adminId;

	@NotNull(message = "수량은 필수입니다.")
	@Min(value = 0, message = "수량은 0 이상이어야 합니다.")
	private Long quantity;
}