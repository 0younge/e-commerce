package com.ecommerce.products.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.common.response.ApiResponse;
import com.ecommerce.products.dto.response.GetProductReviewResponse;
import com.ecommerce.products.service.ProductReviewService;

import lombok.RequiredArgsConstructor;

/**
 * 상품 리뷰 조회 컨트롤러
 *
 * 상품 상세 조회 시 리뷰 통계 및 최신 리뷰를 제공합니다.
 */
@RestController
@RequestMapping("/product-reviews")
@RequiredArgsConstructor
public class ProductReviewController {

	private final ProductReviewService productReviewService;

	/**
	 * 상품 상세 조회 (리뷰 포함)
	 *
	 * @param productId 상품 ID
	 * @return 상품 정보 + 리뷰 통계 + 최신 리뷰 3개
	 */
	@GetMapping("/{productId}")
	public ResponseEntity<ApiResponse<GetProductReviewResponse >> getProductDetail(
		@PathVariable Long productId) {

		GetProductReviewResponse response = productReviewService.getProductDetail(productId);
		return ResponseEntity.ok(ApiResponse.success(response));
	}
}