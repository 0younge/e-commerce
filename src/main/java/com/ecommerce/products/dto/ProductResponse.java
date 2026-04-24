package com.ecommerce.products.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.ecommerce.products.entity.Product;

public record ProductResponse(
	Long productId,
	Long adminId,
	String name,
	String category,
	BigDecimal price,
	Integer quantity,
	String status,
	LocalDateTime createdAt,
	LocalDateTime modifiedAt
) {

	public static ProductResponse from(Product product) {
		return new ProductResponse(
			product.getProductId(),
			product.getAdminId(),
			product.getName(),
			product.getCategory(),
			product.getPrice(),
			product.getQuantity(),
			product.getStatus().name(),
			product.getCreatedAt(),
			product.getModifiedAt()
		);
	}
}