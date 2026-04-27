package com.ecommerce.products.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 상품 등록 요청 DTO
 *
 * 사용: POST /products
 * 용도: 새 상품 등록 시 사용
 *
 * 특징:
 * - status는 자동으로 FOR_SALE 설정됨
 * - adminId로 등록자 지정
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest  {

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
	private Long price;  //

	@NotNull(message = "수량은 필수입니다.")
	@Min(value = 0, message = "수량은 0 이상이어야 합니다.")
	private Long quantity;
}