package com.ecommerce.review.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.orders.entity.Order;
import com.ecommerce.products.entity.Product;
import com.ecommerce.review.dto.GetOneReviewResponse;
import com.ecommerce.review.dto.GetReviewListResponse;
import com.ecommerce.review.entity.Review;
import com.ecommerce.review.repository.ReviewRepository;
import com.ecommerce.users.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
	private final ReviewRepository reviewRepository;

	/**
	 * 키워드와 평점으로 리뷰 목록 조회
	 * @param keyword 검색할 키워드 (null 허용 시 전체 조회)
	 * @param rating 검색할 평점 (null 허용 시 전체 조회)
	 * @param pageable 페이지네이션 정보
	 * @return 페이지네이션을 마친 리뷰 리스트
	 */
	@Transactional(readOnly = true)
	public Page<GetReviewListResponse> findByKeywordAndRating(String keyword, Integer rating, Pageable pageable) {
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

	/**
	 * 특정 리뷰 조회
	 * @param reviewId 조회할 리뷰 아이디
	 * @return 특정 리뷰의 상세 정보
	 * @throws IllegalStateException 리뷰가 존재하지 않을 경우
	 */
	@Transactional(readOnly = true)
	public GetOneReviewResponse findById(Long reviewId) {
		Review review = reviewRepository.findById(reviewId)
			.orElseThrow(() -> new IllegalStateException("리뷰를 찾을 수 없습니다."));
		return GetOneReviewResponse.from(review);
	}

	/**
	 * 리뷰 삭제 (소프트 삭제)
	 * @param reviewId 삭제할 리뷰 아이디
	 * @throws IllegalStateException 리뷰가 존재하지 않을 경우
	 */
	@Transactional
	public void deleteById(Long reviewId) {
		Review review = reviewRepository.findById(reviewId)
			.orElseThrow(() -> new IllegalStateException("리뷰를 찾을 수 없습니다."));
		review.softDelete();
	}
}
