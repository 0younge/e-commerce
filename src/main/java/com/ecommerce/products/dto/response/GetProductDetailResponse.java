package com.ecommerce.products.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.ecommerce.products.entity.Product;

/**
 * 상품 상세 정보 응답 DTO
 *
 * 사용: GET /products/{productId} (상세 조회)
 *
 * 특징:
 * - Admin 상세 정보 포함 (adminId, adminName, adminEmail)
 * - 리뷰 통계 및 최신 리뷰 3개 포함
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
	LocalDateTime modifiedAt,

	GetReviewStatistics reviewStatistics,
	List<GetLatestReview> latestReviews
) {
	/**
	 * Product만으로 변환 (리뷰 없이)
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
			product.getModifiedAt(),

			null,
			List.of()
		);
	}

	/**
	 * Product + 리뷰 정보로 변환
	 */
	public static GetProductDetailResponse of(
		Product product,
		GetReviewStatistics reviewStatistics,
		List<GetLatestReview> latestReviews) {

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
			product.getModifiedAt(),

			reviewStatistics,
			latestReviews
		);
	}
}