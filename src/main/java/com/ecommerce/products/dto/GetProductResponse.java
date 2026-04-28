package com.ecommerce.products.dto;

import java.time.LocalDateTime;

import com.ecommerce.products.entity.Product;


/**
 * 상품 기본 정보 응답 DTO
 *
 * 사용:
 * - GET /products (목록 조회)
 * - POST /products (등록 후 응답)
 * - PUT /products/{id} (수정 후 응답)
 * - PATCH /products/{id}/quantity (재고 변경 후 응답)
 *
 * 특징:
 * - Admin 정보 포함 (adminId, adminName)
 * - Admin이 null일 경우 안전하게 처리
 */
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


		Long adminId = null;
		String adminName = null;

		if (product.getAdmin() != null) {
			adminId = product.getAdmin().getAdminId();
			adminName = product.getAdmin().getName();
		}

		return new GetProductResponse(
			product.getProductId(),
			adminId,
			adminName,
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