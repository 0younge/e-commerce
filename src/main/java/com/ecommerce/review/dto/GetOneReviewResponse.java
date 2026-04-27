package com.ecommerce.review.dto;

import java.time.LocalDateTime;

import com.ecommerce.products.entity.Product;
import com.ecommerce.review.entity.Review;
import com.ecommerce.users.entity.User;

import lombok.Getter;

@Getter
public class GetOneReviewResponse {

	private final String productName;
	private final String userName;
	private final String userEmail;
	private final LocalDateTime createdAt;
	private final int rating;
	private final String content;

	private GetOneReviewResponse(String productName, String userName, String userEmail, LocalDateTime createdAt,
		int rating, String content) {
		this.productName = productName;
		this.userName = userName;
		this.userEmail = userEmail;
		this.createdAt = createdAt;
		this.rating = rating;
		this.content = content;
	}

	public static GetOneReviewResponse from(Review review) {
		Product product = review.getProduct();
		User user = review.getUser();
		return new GetOneReviewResponse(
			product.getName(),
			user.getName(),
			user.getEmail(),
			review.getCreatedAt(),
			review.getRating(),
			review.getContent()
		);
	}
}
