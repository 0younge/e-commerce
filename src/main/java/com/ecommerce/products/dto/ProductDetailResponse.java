package com.ecommerce.products.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.ecommerce.admins.entity.Admin;
import com.ecommerce.products.entity.Product;

/**
 * 상품 상세 조회 응답 DTO
 * Admin 정보 포함
 */
public record ProductDetailResponse(
	// Product 정보
	Long productId,
	String name,
	String category,
	BigDecimal price,
	Integer quantity,
	String status,

	// Admin 정보 (직접 포함)
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
			product.getStatus().name(),
			admin.getAdminId(),
			admin.getName(),
			admin.getEmail(),
			product.getCreatedAt(),
			product.getModifiedAt()
		);
	}
}

