package com.ecommerce.products.dto;

import java.math.BigDecimal;

import com.ecommerce.products.entity.Product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProductRequest {

	@NotNull(message = "관리자 ID는 필수입니다.")
	private Long adminId;

	@NotBlank(message = "상품명은 필수입니다.")
	@Size(max = 255)
	private String name;

	@NotBlank(message = "카테고리는 필수입니다.")
	@Size(max = 50)
	private String category;

	@NotNull(message = "가격은 필수입니다.")
	@DecimalMin(value = "0.0")
	@Digits(integer = 8, fraction = 2)
	private BigDecimal price;

	@NotNull(message = "수량은 필수입니다.")
	@Min(value = 0)
	private Integer quantity;  // ← 이거 있어야 함!



}

