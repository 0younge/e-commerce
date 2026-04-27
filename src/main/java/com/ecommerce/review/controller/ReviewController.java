package com.ecommerce.review.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.common.response.ApiResponse;
import com.ecommerce.review.dto.GetPageResponse;
import com.ecommerce.review.dto.GetReviewListResponse;
import com.ecommerce.review.service.ReviewService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ReviewController {
	private final ReviewService reviewService;

	@GetMapping("/reviews")
	public ResponseEntity<ApiResponse<GetPageResponse<GetReviewListResponse>>> getReviewList(
		@RequestParam(required = false) String keyword,
		@RequestParam(defaultValue = "1") int page,
		@RequestParam(defaultValue = "10") int size,
		@RequestParam(required = false, defaultValue = "createdAt") String sortBy,
		@RequestParam(required = false, defaultValue = "ASC") String sortOrder,
		@RequestParam(required = false) Integer rating) {

		Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.valueOf(sortOrder), sortBy);
		Page<GetReviewListResponse> result = reviewService.findByKeywordAndRating(keyword, rating, pageable);
		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(GetPageResponse.of(result)));
	}

	@GetMapping("/reviews/{reviewId}")
	public ResponseEntity<ApiResponse<GetOneReviewResponse>> getOneReview(@PathVariable Long reviewId) {
		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(reviewService.findById(reviewId)));
	}

	@DeleteMapping("/reviews/{reviewId}")
	public ResponseEntity<ApiResponse<Void>> deleteReview(@PathVariable Long reviewId,
		@SessionAttribute(name = AdminConst.ADMIN_INFO, required = false) AdminInfo adminInfo) {

		if (adminInfo == null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "관리자 로그인이 필요합니다.");
		}
		reviewService.deleteById(reviewId);
		return ResponseEntity.ok(ApiResponse.success("리뷰가 삭제되었습니다."));
	}
}
