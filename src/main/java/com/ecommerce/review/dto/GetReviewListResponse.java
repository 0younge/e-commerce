package com.ecommerce.review.dto;

import java.time.LocalDateTime;

import com.ecommerce.orders.entity.Order;
import com.ecommerce.products.entity.Product;
import com.ecommerce.review.entity.Review;
import com.ecommerce.users.entity.User;

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

	private GetReviewListResponse(Long reviewId, String orderNumber, String userName, String productName, int rating,
		String content, LocalDateTime createdAt) {
		this.reviewId = reviewId;
		this.orderNumber = orderNumber;
		this.userName = userName;
		this.productName = productName;
		this.rating = rating;
		this.content = content;
		this.createdAt = createdAt;
	}

	public static GetReviewListResponse from(Review review) {
		Order order = review.getOrder();
		Product product = review.getProduct();
		User user = review.getUser();

		return new GetReviewListResponse(
			review.getReviewId(),
			order.getNumber(),
			user.getName(),
			product.getName(),
			review.getRating(),
			review.getContent(),
			review.getCreatedAt()
		);
	}
}
