package com.ecommerce.review.dto;

import java.time.LocalDateTime;

import com.ecommerce.review.entity.Review;

import lombok.Getter;

@Getter
public class ReviewResponse {
	private final String userName;
	private final int rating;
	private final String content;
	private final LocalDateTime createdAt;

	public ReviewResponse(String userName, int rating, String content, LocalDateTime createdAt) {
		this.userName = userName;
		this.rating = rating;
		this.content = content;
		this.createdAt = createdAt;
	}

	public static ReviewResponse from(Review review) {
		return new ReviewResponse(
			review.getUser().getName(),
			review.getRating(),
			review.getContent(),
			review.getCreatedAt()
		);
	}
}
