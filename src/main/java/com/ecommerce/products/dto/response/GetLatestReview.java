package com.ecommerce.products.dto.response;

import java.time.LocalDateTime;

import com.ecommerce.review.entity.Review;

/**
 * 최신 리뷰 조회 응답
 */
public record GetLatestReview(
	String userName,        // 고객명
	Integer rating,         // 평점
	String content,         // 리뷰 내용
	LocalDateTime createdAt // 작성일
) {
	/**
	 * Review Entity → DTO 변환
	 */
	public static GetLatestReview from(Review review) {
		return new GetLatestReview(
			review.getUser().getName(),
			review.getRating(),
			review.getContent(),
			review.getCreatedAt()
		);
	}
}