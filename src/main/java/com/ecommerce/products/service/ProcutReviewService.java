package com.ecommerce.products.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.common.exception.ProductNotFoundException;
import com.ecommerce.products.dto.response.GetLatestReview;
import com.ecommerce.products.dto.response.GetProductDetailResponse;
import com.ecommerce.products.dto.response.GetReviewStatistics;
import com.ecommerce.products.entity.Product;
import com.ecommerce.products.repository.ProductRepository;
import com.ecommerce.review.entity.Review;
import com.ecommerce.review.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

/**
 * 상품 리뷰 조회 서비스
 *
 * <p>상품 상세 조회 시 리뷰 통계 및 최신 리뷰를 제공합니다.</p>
 */
@Service
@RequiredArgsConstructor
public class ProcutReviewService {

	private final ReviewRepository reviewRepository;
	private final ProductRepository productRepository;


	/**
	 * 상품 상세 조회 (리뷰 포함)
	 */
	@Transactional(readOnly = true)
	public GetProductDetailResponse getProductDetail(Long productId) {

		
		Product product = productRepository.findById(productId)
			.orElseThrow(() -> new ProductNotFoundException());

		
		GetReviewStatistics statistics = calculateReviewStatistics(productId);

		
		List<GetLatestReview> latestReviews = getLatestReviews(productId, 3);

		
		return GetProductDetailResponse.of(product, statistics, latestReviews);
	}

	/**
	 * 최신 리뷰 조회 (내림차순)
	 */
	private List<GetLatestReview> getLatestReviews(Long productId, int limit) {

		List<Review> reviews = productRepository.findRevuewsByProductId(productId);

		List<GetLatestReview> latestReviews = reviews.stream()
			.sorted((r1, r2) -> r2.getCreatedAt().compareTo(r1.getCreatedAt()))
			.limit(limit)
			.map(GetLatestReview::from)
			.toList();

		return latestReviews;
	}

	/**
	 * 리뷰 통계 계산
	 */
	private GetReviewStatistics calculateReviewStatistics(Long prodcutId){

		List<Review> reviews = productRepository.findRevuewsByProductId(prodcutId);

		if (reviews.isEmpty()){
			return GetReviewStatistics.of(0.0, 0L, Map.of());
		}

		Double avg = reviews.stream()
			.mapToInt(Review::getRating)
			.average()
			.orElse(0.0);

		Long total = (long) reviews.size();

		Map<Integer, Long> counts = reviews.stream()
			.collect(Collectors.groupingBy(
				Review::getRating,
				Collectors.counting()
			));

		return GetReviewStatistics.of(avg, total, counts);
	}




}
