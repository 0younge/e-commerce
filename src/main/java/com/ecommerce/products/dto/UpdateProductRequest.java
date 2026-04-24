package com.ecommerce.products.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateProductRequest {

	private String name;
	private String category;
	private BigDecimal price;

}
