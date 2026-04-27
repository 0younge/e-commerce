package com.ecommerce.review.dto;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class GetReviewListResponse {

	private final Long reviewId;

	private final String orderNumber;

	private final String userName;

	private final String productName;

	private final int rating;

	private final String content;

	private final LocalDateTime createdAt;

	public GetReviewListResponse(Long reviewId, String orderNumber, String userName, String productName, int rating,
		String content, LocalDateTime createdAt) {
		this.reviewId = reviewId;
		this.orderNumber = orderNumber;
		this.userName = userName;
		this.productName = productName;
		this.rating = rating;
		this.content = content;
		this.createdAt = createdAt;
	}
}
