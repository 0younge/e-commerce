package com.ecommerce.products.dto;

import java.math.BigDecimal;

import lombok.Getter;

@Getter
public class UpdateProductResponse {

	private Long productId;
	private String name;
	private String category;
	private BigDecimal price;
	private Integer quantity;
	private String status;
}
