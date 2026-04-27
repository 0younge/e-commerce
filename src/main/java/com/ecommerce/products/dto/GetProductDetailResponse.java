package com.ecommerce.products.dto;

import java.time.LocalDateTime;

import com.ecommerce.admins.entity.Admin;
import com.ecommerce.products.entity.Product;

public record GetProductDetailResponse(
	Long productId,
	String name,
	String category,
	Long price,
	Long quantity,
	String status,

	Long adminId,
	String adminName,
	String adminEmail,

	LocalDateTime createdAt,
	LocalDateTime modifiedAt
) {
	public static GetProductDetailResponse from(Product product, Admin admin) {

		return new GetProductDetailResponse(
			product.getProductId(),
			product.getName(),
			product.getCategory(),
			product.getPrice(),
			product.getQuantity(),
			product.getStatus(),
			admin.getAdminId(),
			admin.getName(),
			admin.getEmail(),
			product.getCreatedAt(),
			product.getModifiedAt()
		);
	}
}