package com.ecommerce.review.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.admins.entity.AdminConst;
import com.ecommerce.admins.entity.AdminInfo;
import com.ecommerce.common.exception.InvalidRequestException;
import com.ecommerce.common.response.ApiResponse;
import com.ecommerce.review.dto.GetOneReviewResponse;
import com.ecommerce.review.dto.GetPageResponse;
import com.ecommerce.review.dto.GetReviewListResponse;
import com.ecommerce.review.service.ReviewService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ReviewController {
	private final ReviewService reviewService;

	/**
	 * 리뷰 목록 조회
	 * @param keyword 검색할 키워드
	 * @param page 페이지 번호
	 * @param size 페이지 사이즈
	 * @param sortBy 정렬 기준
	 * @param sortOrder 정렬 순서
	 * @param rating 검색할 평점
	 * @return 페이지네이션을 마친 리뷰 리스트
	 */
	@GetMapping("/reviews")
	public ResponseEntity<ApiResponse<GetPageResponse<GetReviewListResponse>>> getReviewList(
		@RequestParam(required = false) String keyword,
		@RequestParam(defaultValue = "1") int page,
		@RequestParam(defaultValue = "10") int size,
		@RequestParam(required = false, defaultValue = "createdAt") String sortBy,
		@RequestParam(required = false, defaultValue = "ASC") String sortOrder,
		@RequestParam(required = false) Integer rating) {

		if (page < 1) {
			throw new InvalidRequestException("페이지는 1 이상이어야 합니다.");
		}

		Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.valueOf(sortOrder.toUpperCase()), sortBy);
		Page<GetReviewListResponse> result = reviewService.findByKeywordAndRating(keyword, rating, pageable);
		return ResponseEntity.ok(ApiResponse.success(GetPageResponse.of(result)));
	}

	/**
	 * 특정 리뷰 조회
	 * @param reviewId 조회할 리뷰 아이디
	 * @return 특정 리뷰의 상세 정보
	 */
	@GetMapping("/reviews/{reviewId}")
	public ResponseEntity<ApiResponse<GetOneReviewResponse>> getOneReview(@PathVariable Long reviewId) {
		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(reviewService.findById(reviewId)));
	}

	/**
	 * 리뷰 삭제
	 * @param reviewId 삭제할 리뷰 아이디
	 * @param adminInfo 세션에 저장된 관리자 정보
	 * @return 삭제 결과 메시지
	 */
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
