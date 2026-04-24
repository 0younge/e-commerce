package com.ecommerce.products.dto;

import java.time.LocalDateTime;

import com.ecommerce.admins.entity.Admin;
import com.ecommerce.products.entity.Product;

public record ProductDetailResponse(
	Long productId,
	String name,
	String category,
	Long price,  // ← BigDecimal → Long
	Long quantity,  // ← Integer → Long
	String status,

	Long adminId,
	String adminName,
	String adminEmail,

	LocalDateTime createdAt,
	LocalDateTime modifiedAt
) {
	public static ProductDetailResponse from(Product product, Admin admin) {
		return new ProductDetailResponse(
			product.getProductId(),
			product.getName(),
			product.getCategory(),
			product.getPrice(),
			product.getQuantity(),
			product.getStatus(),  // ← .name() 삭제
			admin.getAdminId(),
			admin.getName(),
			admin.getEmail(),
			product.getCreatedAt(),
			product.getModifiedAt()
		);
	}
}