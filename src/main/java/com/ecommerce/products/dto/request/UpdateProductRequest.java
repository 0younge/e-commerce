package com.ecommerce.products.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 상품 정보 수정 요청 DTO
 *
 * 사용: PUT /products/{id}
 * 용도: 상품의 기본 정보(이름, 카테고리, 가격) 수정
 *
 * 특징:
 * - quantity는 별도 API로 변경 (PATCH /products/{id}/quantity)
 * - adminId로 권한 체크
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductRequest {

	@NotNull(message = "관리자 ID는 필수입니다.")
	private Long adminId;

	@NotBlank(message = "상품명은 필수입니다.")
	@Size(max = 100, message = "상품명은 100자 이하여야 합니다.")
	private String name;

	@NotBlank(message = "카테고리는 필수입니다.")
	@Size(max = 50, message = "카테고리는 50자 이하여야 합니다.")
	private String category;

	@NotNull(message = "가격은 필수입니다.")
	@Min(value = 0, message = "가격은 0 이상이어야 합니다.")
	private Long price;
}