package com.ecommerce.products.dto;

import java.time.LocalDateTime;

import com.ecommerce.products.entity.Product;

public record ProductResponse(
	Long productId,
	Long adminId,
	String adminName,
	String name,
	String category,
	Long price,
	Long quantity,
	String status,
	LocalDateTime createdAt,
	LocalDateTime modifiedAt
) {
	public static ProductResponse from(Product product) {
		return new ProductResponse(
			product.getProductId(),
			product.getAdmin().getAdminId(),
			product.getAdmin().getName(),
			product.getName(),
			product.getCategory(),
			product.getPrice(),
			product.getQuantity(),
			product.getStatus(),
			product.getCreatedAt(),
			product.getModifiedAt()
		);
	}
}