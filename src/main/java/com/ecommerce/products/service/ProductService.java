package com.ecommerce.products.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.admins.entity.Admin;
import com.ecommerce.admins.repository.AdminRepository;
import com.ecommerce.common.exception.AdminNotFoundException;
import com.ecommerce.common.exception.ProductNotFoundException;
import com.ecommerce.products.dto.CreateProductRequest;
import com.ecommerce.products.dto.GetProductDetailResponse;
import com.ecommerce.products.dto.GetProductResponse;
import com.ecommerce.products.dto.UpdateProductRequest;
import com.ecommerce.products.dto.UpdateQuantityRequest;
import com.ecommerce.products.entity.Product;
import com.ecommerce.products.repository.ProductRepository;
import com.ecommerce.review.dto.ReviewResponse;
import com.ecommerce.review.dto.ReviewStats;
import com.ecommerce.review.entity.Review;
import com.ecommerce.review.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	private final AdminRepository adminRepository;
	private final ReviewRepository reviewRepository;

	/**
	 * 상품 등록
	 *
	 * @param request 상품 등록 요청 (이름, 카테고리, 가격, 재고, 상태, 관리자ID)
	 * @return 등록된 상품 정보
	 * @throws AdminNotFoundException 관리자 권한이 없습니다.
	 */
	@Transactional
	public GetProductResponse save(CreateProductRequest request, Long adminId) {

		Admin admin = adminRepository.findById(adminId)
			.orElseThrow(() -> new AdminNotFoundException("관리자 권한이 없습니다."));

		Product product = new Product(
			request.name(),
			request.category(),
			request.price(),
			request.quantity(),
			admin
		);

		Product savedProduct = productRepository.save(product);

		return GetProductResponse.from(savedProduct);
	}

	/**
	 * 상품 목록 조회 (검색 + 필터링 + 페이징)
	 *
	 * @param pageable 페이징 정보 (page, size, sort)
	 * @param name 상품명 검색 (부분 일치, optional)
	 * @param category 카테고리 필터 (optional)
	 * @param status 상태 필터 (optional)
	 * @return 페이징된 상품 목록
	 *
	 * 예시:
	 * - GET /products?page=0&size=10
	 * - GET /products?name=노트북&category=전자기기
	 * - GET /products?status=FOR_SALE&page=0&size=20
	 */
	/**
	 * 상품 목록 조회 (검색 + 필터링 + 페이징)
	 */
	@Transactional(readOnly = true)
	public Page<GetProductResponse> findAllPaged(
		Pageable pageable,
		String name,
		String category,
		String status) {

		Page<Product> productPage = productRepository.searchProducts(
			name,
			category,
			status,
			pageable
		);

		return productPage.map(GetProductResponse::from);
	}

	/**
	 * 상품 상세 조회
	 *
	 * @param productId 상품 ID
	 * @return 상품 상세 정보 (관리자 정보 포함)
	 * @throws ProductNotFoundException 존재하지 않는 상품
	 */
	@Transactional(readOnly = true)
	public GetProductDetailResponse getProductDetail(Long productId) {
		Product product = productRepository.findById(productId)
			.orElseThrow(ProductNotFoundException::new);

		Admin admin = product.getAdmin();

		List<Review> reviews = reviewRepository.findByProductProductId(productId);

		double avg = reviews.stream().mapToDouble(Review::getRating).average().orElse(0.0);

		ReviewStats reviewStats = new ReviewStats(
			Math.round(avg * 10) / 10.0,
			reviews.size(),
			(int)reviews.stream().filter(r -> r.getRating() == 1).count(),
			(int)reviews.stream().filter(r -> r.getRating() == 2).count(),
			(int)reviews.stream().filter(r -> r.getRating() == 3).count(),
			(int)reviews.stream().filter(r -> r.getRating() == 4).count(),
			(int)reviews.stream().filter(r -> r.getRating() == 5).count()
		);

		List<ReviewResponse> reviewResponses = reviewRepository
			.findTop3ByProductProductIdOrderByCreatedAtDesc(productId)
			.stream()
			.map(ReviewResponse::from)
			.toList();

		return GetProductDetailResponse.from(product, admin, reviewStats, reviewResponses);
	}

	/**
	 * 재고 변경 (관리자 전용)
	 *
	 * @param productId 상품 ID
	 * @param request 변경할 재고 정보
	 *
	 * @return 변경된 상품 정보
	 */
	@Transactional
	public GetProductResponse updateQuantity(Long productId, UpdateQuantityRequest request, Long adminId) {

		Product product = productRepository.findById(productId)
			.orElseThrow(ProductNotFoundException::new);

		if (!product.getAdmin().getAdminId().equals(adminId)) {
			throw new AdminNotFoundException("관리자 권한이 없습니다..");
		}

		product.updateQuantity(request.quantity());

		return GetProductResponse.from(product);
	}

	/**
	 * 상품 수정
	 * 권한: Admin만 가능
	 *
	 * @param id 수정할 상품 ID
	 * @param request 수정할 상품 정보 (이름, 카테고리, 가격, 재고, 상태, 관리자ID)
	 * @return 수정된 상품 정보
	 * @throws ProductNotFoundException 존재하지 않는 상품
	 */
	@Transactional
	public GetProductResponse update(Long id, UpdateProductRequest request, Long adminId) {

		Admin admin = adminRepository.findById(adminId)
			.orElseThrow(() -> new AdminNotFoundException("관리자 권한이 없습니다."));

		Product product = productRepository.findById(id)
			.orElseThrow(ProductNotFoundException::new);

		product.update(
			request.name(),
			request.category(),
			request.price()
		);

		return GetProductResponse.from(product);
	}

	/**
	 * 상품 삭제
	 * 권한: Admin만 가능 (본인이 등록한 상품만)
	 *
	 * @param productId 삭제할 상품 ID
	 * @param adminId 요청한 관리자 ID (권한 확인용)
	 * @throws AdminNotFoundException 권한 없음
	 * @throws ProductNotFoundException 존재하지 않는 상품
	 */
	@Transactional
	public void delete(Long productId, Long adminId) {

		Admin admin = adminRepository.findById(adminId)
			.orElseThrow(() -> new AdminNotFoundException("관리자 권한이 없습니다."));

		Product product = productRepository.findById(productId)
			.orElseThrow(ProductNotFoundException::new);

		if (!product.getAdmin().getAdminId().equals(adminId)) {
			throw new AdminNotFoundException("관리자 권한이 없습니다.");
		}

		product.softDelete();

	}
}

