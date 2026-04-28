package com.ecommerce.products.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.ecommerce.admins.entity.Admin;
import com.ecommerce.products.entity.Product;
import com.ecommerce.review.dto.ReviewResponse;
import com.ecommerce.review.dto.ReviewStats;

/**
 * 상품 상세 정보 응답 DTO
 *
 * 사용: GET /products/{productId} (상세 조회)
 *
 * 특징:
 * - Admin 상세 정보 포함 (adminId, adminName, adminEmail)
 * - GetProductResponse보다 더 많은 Admin 정보 제공
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

	double ratingAverage,
	int totalReviewsCount,
	int ratingCount1,
	int ratingCount2,
	int ratingCount3,
	int ratingCount4,
	int ratingCount5,

	List<ReviewResponse> reviews,

	LocalDateTime createdAt,
	LocalDateTime modifiedAt

) {
	public static GetProductDetailResponse from(Product product, Admin admin, ReviewStats reviewStats,
		List<ReviewResponse> reviews) {

		return new GetProductDetailResponse(
			product.getProductId(),
			product.getName(),
			product.getCategory(),
			product.getPrice(),
			product.getQuantity(),
			product.getStatus().name(),
			admin.getAdminId(),
			admin.getName(),
			admin.getEmail(),
			reviewStats.getRatingAverage(),
			reviewStats.getTotalReviewsCount(),
			reviewStats.getRatingCount1(),
			reviewStats.getRatingCount2(),
			reviewStats.getRatingCount3(),
			reviewStats.getRatingCount4(),
			reviewStats.getRatingCount5(),
			reviews,
			product.getCreatedAt(),
			product.getModifiedAt()
		);
	}
}