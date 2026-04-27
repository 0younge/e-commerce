package com.ecommerce.products.dto;

import java.time.LocalDateTime;

import com.ecommerce.products.entity.Product;

public record GetProductResponse(
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
	public static GetProductResponse from(Product product) {
		return new GetProductResponse(
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