package com.ecommerce.products.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 재고 변경 요청 DTO
 *
 * 사용: PATCH /products/{id}/quantity
 * 용도: 상품 재고 수량 변경
 *
 * 특징:
 * - quantity = 0 → status 자동 SOLD_OUT
 * - quantity > 0 → status 자동 FOR_SALE
 * - adminId로 권한 체크
 */
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