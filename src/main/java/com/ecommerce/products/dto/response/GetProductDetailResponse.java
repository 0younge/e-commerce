package com.ecommerce.products.dto.response;

import java.time.LocalDateTime;

import com.ecommerce.products.entity.Product;

/**
 * 상품 상세 정보 응답 DTO
 *
 * 사용: GET /products/{productId} (상세 조회)
 *
 * 특징:
 * - Admin 상세 정보 포함 (adminId, adminName, adminEmail)
 */
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
	/**
	 * Product Entity → DTO 변환
	 */
	public static GetProductDetailResponse from(Product product) {
		return new GetProductDetailResponse(
			product.getProductId(),
			product.getName(),
			product.getCategory(),
			product.getPrice(),
			product.getQuantity(),
			product.getStatus().name(),

			product.getAdmin().getAdminId(),
			product.getAdmin().getName(),
			product.getAdmin().getEmail(),

			product.getCreatedAt(),
			product.getModifiedAt()
		);
	}
}