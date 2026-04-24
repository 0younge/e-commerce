package com.ecommerce.products.controller;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.common.enums.ProductStatus;
import com.ecommerce.products.dto.ProductDetailResponse;
import com.ecommerce.products.dto.ProductRequest;
import com.ecommerce.products.dto.ProductResponse;
import com.ecommerce.products.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * 상품 Controller
 *
 * 상품 관련 API 엔드포인트 제공
 */
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	/**
	 * 상품 등록
	 *
	 * POST /products
	 *
	 * @param request 상품 등록 요청 정보
	 * @return 201 Created, 등록된 상품 정보
	 */
	@PostMapping
	public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {

		ProductResponse response = productService.save(request);

		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(response);
	}

	/**
	 * 상품 목록 조회 (검색 + 필터 + 페이징)
	 *
	 * GET /products
	 * GET /products?page=0&size=10
	 * GET /products?name=노트북
	 * GET /products?category=전자기기&status=FOR_SALE
	 */
	@GetMapping
	public ResponseEntity<Page<ProductResponse>> getProducts(
		Pageable pageable,
		@RequestParam(required = false) String name,
		@RequestParam(required = false) String category,
		@RequestParam(required = false) ProductStatus status) {

		Page<ProductResponse> response = productService.findAllPaged(
			pageable,
			name,
			category,
			status
		);
		return ResponseEntity.ok(response);
	}

	/**
	 * 상품 상세 조회
	 *
	 * GET /products/{productId}
	 *
	 * @param productId 상품 ID
	 * @return 200 OK, 상품 상세 정보 (관리자 정보 포함)
	 */
	@GetMapping("/{Id}")
	public ResponseEntity<ProductDetailResponse> getProductDatall(

		@Valid @PathVariable Long Id) {

		return ResponseEntity.ok(productService.getProductDatall(Id));
	}
}
