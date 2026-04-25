package com.ecommerce.products.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.admins.entity.Admin;
import com.ecommerce.admins.repository.AdminRepository;
import com.ecommerce.common.exception.BusinessException;
import com.ecommerce.common.exception.InvalidRequestException;
import com.ecommerce.common.exception.ProductNotFoundException;
import com.ecommerce.products.dto.ProductDetailResponse;
import com.ecommerce.products.dto.ProductRequest;
import com.ecommerce.products.dto.ProductResponse;
import com.ecommerce.products.entity.Product;
import com.ecommerce.products.repository.ProductRepository;

import at.favre.lib.crypto.bcrypt.IllegalBCryptFormatException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	private final AdminRepository adminRepository;

	/**
	 * 상품 등록
	 *
	 * @param request 상품 등록 요청 (이름, 카테고리, 가격, 재고, 상태, 관리자ID)
	 * @return 등록된 상품 정보
	 * @throws InvalidRequestException 존재하지 않는 관리자ID
	 */
	@Transactional
	public ProductResponse save(ProductRequest request) {

		Admin admin = adminRepository.findById(request.getAdminId())
			.orElseThrow(() -> new InvalidRequestException("존재하지 않는 관리자입니다."));

		Product product = new Product(
			request.getName(),
			request.getCategory(),
			request.getPrice(),
			request.getQuantity(),
			request.getStatus(),
			admin
		);

		return ProductResponse.from(productRepository.save(product));
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
	@Transactional(readOnly = true)
	public Page<ProductResponse> findAllPaged(
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

		return productPage.map(ProductResponse::from);
	}

	/**
	 * 상품 상세 조회
	 *
	 * @param productId 상품 ID
	 * @return 상품 상세 정보 (관리자 정보 포함)
	 * @throws ProductNotFoundException 존재하지 않는 상품
	 */
	@Transactional(readOnly = true)
	public ProductDetailResponse getProductDetail(Long productId) {

		Product product = productRepository.findById(productId)
			.orElseThrow(ProductNotFoundException::new);

		Admin admin = product.getAdmin();

		return ProductDetailResponse.from(product, admin);
	}


	//재고변경로직

	//사품상태 자동변경(품절/판매중)

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
	public ProductResponse update(Long id, ProductRequest request) {

		Admin admin = adminRepository.findById(request.getAdminId())
			.orElseThrow(() -> new InvalidRequestException("존재하지 않는 관리자입니다."));

		Product product = productRepository.findById(id)
			.orElseThrow(() -> new ProductNotFoundException());

		product.Update(
			request.getName(),
			request.getCategory(),
			request.getPrice(),
			admin

		);

		return ProductResponse.from(product);
	}

	/**
	 * 상품 삭제
	 * 권한: Admin만 가능 (본인이 등록한 상품만)
	 *
	 * @param productId 삭제할 상품 ID
	 * @param adminId 요청한 관리자 ID (권한 확인용)
	 * @throws InvalidRequestException 존재하지 않는 관리자 또는 권한 없음
	 * @throws ProductNotFoundException 존재하지 않는 상품
	 */
	@Transactional
	public void delete(Long productId, Long adminId) {

		Admin admin = adminRepository.findById(adminId)
			.orElseThrow(() -> new InvalidRequestException("존재하지 않는 관리자입니다."));

		Product product = productRepository.findById(productId)
			.orElseThrow(() -> new ProductNotFoundException());

		if (!product.getAdmin().getAdminId().equals(adminId)){

			throw new InvalidRequestException("본인이 등록한 상품만 삭제할 수 있습니다.");
		}

		productRepository.delete(product);
	}

}