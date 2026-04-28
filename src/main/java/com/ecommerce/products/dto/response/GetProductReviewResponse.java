package com.ecommerce.products.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.ecommerce.products.entity.Product;

/**
 * 상품 + 리뷰 정보 응답 DTO
 *
 * 사용: GET /product-reviews/{productId}
 *
 * 특징:
 * - Admin 정보 제외
 * - 리뷰 통계 및 최신 리뷰 3개 포함
 */
public record GetProductReviewResponse(
	Long productId,
	String name,
	String category,
	Long price,
	Long quantity,
	String status,

	LocalDateTime createdAt,
	LocalDateTime modifiedAt,

	GetReviewStatistics reviewStatistics,
	List<GetLatestReview> latestReviews
) {
	/**
	 * Product + 리뷰 정보로 변환
	 */
	public static GetProductReviewResponse of(
		Product product,
		GetReviewStatistics reviewStatistics,
		List<GetLatestReview> latestReviews) {

		return new GetProductReviewResponse(
			product.getProductId(),
			product.getName(),
			product.getCategory(),
			product.getPrice(),
			product.getQuantity(),
			product.getStatus().name(),

			product.getCreatedAt(),
			product.getModifiedAt(),

			reviewStatistics,
			latestReviews
		);
	}
}