package com.ecommerce.review.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.orders.entity.Order;
import com.ecommerce.products.entity.Product;
import com.ecommerce.review.dto.GetReviewListResponse;
import com.ecommerce.review.repository.ReviewRepository;
import com.ecommerce.users.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
	private final ReviewRepository reviewRepository;

	@Transactional(readOnly = true)
	public Page<GetReviewListResponse> findByKeywordAndRating(String keyword, int rating, Pageable pageable) {
		return reviewRepository.findByKeywordAndRating(keyword, rating, pageable)
			.map(review -> {
				Order order = review.getOrder();
				User user = review.getUser();
				Product product = review.getProduct();
				return new GetReviewListResponse(
					review.getReviewId(),
					order.getNumber(),
					user.getName(),
					product.getName(),
					review.getRating(),
					review.getContent(),
					review.getCreatedAt()
				);
			});
	}
}
