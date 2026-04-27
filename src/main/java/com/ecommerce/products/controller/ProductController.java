package com.ecommerce.products.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.common.response.ApiResponse;
import com.ecommerce.products.dto.CreateProductRequest;
import com.ecommerce.products.dto.GetProductDetailResponse;
import com.ecommerce.products.dto.GetProductResponse;
import com.ecommerce.products.dto.UpdateProductRequest;
import com.ecommerce.products.dto.UpdateQuantityRequest;
import com.ecommerce.products.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * 상품 Controller
 * 상품 관련 API 엔드포인트 제공
 */
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	/**
	 * 상품 등록
	 * POST /products
	 */
	@PostMapping
	public ResponseEntity<ApiResponse<GetProductResponse>> createProduct(
		@Valid @RequestBody CreateProductRequest request) {

		GetProductResponse response = productService.save(request);

		return ResponseEntity.ok(ApiResponse.created("상품이 등록되었습니다.", response));

	}

	/**
	 * 상품 목록 조회 (검색 + 필터 + 페이징)
	 * GET /products
	 */
	@GetMapping
	public ResponseEntity<ApiResponse<Page<GetProductResponse>>> getProducts(
		Pageable pageable,
		@RequestParam(required = false) String name,
		@RequestParam(required = false) String category,
		@RequestParam(required = false) String status) {

		Page<GetProductResponse> response = productService.findAllPaged(
			pageable,
			name,
			category,
			status
		);

		return ResponseEntity.ok(ApiResponse.success(response));
	}

	/**
	 * 상품 상세 조회
	 * GET /products/{productId}
	 */
	@GetMapping("/{productId}")
	public ResponseEntity<ApiResponse<GetProductDetailResponse>> getProductDetail(
		@PathVariable Long productId) {

		return ResponseEntity.ok(
			ApiResponse.success(productService.getProductDetail(productId))
		);
	}

	/**
	 * 상품 수정
	 * PUT /products/{id}
	 */
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<GetProductResponse>> update(
		@PathVariable Long id,
		@Valid @RequestBody UpdateProductRequest request) {

		return ResponseEntity.ok(
			ApiResponse.success("상품이 수정되었습니다.", productService.update(id, request))
		);
	}

	/**
	 * 재고 변경
	 * PATCH /products/{id}/quantity
	 */
	@PatchMapping("/{id}/quantity")
	public ResponseEntity<ApiResponse<GetProductResponse>> updateQuantity(
		@PathVariable Long id,
		@Valid @RequestBody UpdateQuantityRequest request) {

		return ResponseEntity.ok(
			ApiResponse.success("재고가 변경되었습니다.", productService.updateQuantity(id, request))
		);
	}

	/**
	 * 상품 삭제
	 * DELETE /products/{productId}?adminId={adminId}
	 */
	@DeleteMapping("/{productId}")
	public ResponseEntity<ApiResponse<Void>> delete(
		@PathVariable Long productId,
		@RequestParam Long adminId) {

		productService.delete(productId, adminId);

		return ResponseEntity.ok(ApiResponse.success("상품이 삭제되었습니다."));
	}
}