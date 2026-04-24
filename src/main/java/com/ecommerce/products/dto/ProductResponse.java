package com.ecommerce.products.dto;

import java.time.LocalDateTime;

import com.ecommerce.products.entity.Product;

public record ProductResponse(
	Long productId,
	Long adminId,  // ← Admin 객체에서 가져오기
	String name,
	String category,
	Long price,  // ← BigDecimal → Long
	Long quantity,  // ← Integer → Long
	String status,  // ← ProductStatus → String
	LocalDateTime createdAt,
	LocalDateTime modifiedAt
) {
	public static ProductResponse from(Product product) {
		return new ProductResponse(
			product.getProductId(),
			product.getAdmin().getAdminId(),  // ← admin 객체에서 ID 추출
			product.getName(),
			product.getCategory(),
			product.getPrice(),
			product.getQuantity(),
			product.getStatus(),  // ← .name() 삭제
			product.getCreatedAt(),
			product.getModifiedAt()
		);
	}
}