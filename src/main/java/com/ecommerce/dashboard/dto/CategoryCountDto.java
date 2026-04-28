package com.ecommerce.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryCountDto {

	private final String category;
	private final Long count;

}
