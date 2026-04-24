package com.ecommerce.products.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.admins.entity.Admin;
import com.ecommerce.admins.repository.AdminRepository;
import com.ecommerce.common.exception.InvalidRequestException;
import com.ecommerce.common.exception.ProductNotFoundException;
import com.ecommerce.products.dto.ProductDetailResponse;
import com.ecommerce.products.dto.ProductRequest;
import com.ecommerce.products.dto.ProductResponse;
import com.ecommerce.products.entity.Product;
import com.ecommerce.products.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	private final AdminRepository adminRepository;

	/**
	 * 상품 등록
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
	 * 상품 목록 조회
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
	 */
	@Transactional(readOnly = true)
	public ProductDetailResponse getProductDetail(Long productId) {

		Product product = productRepository.findById(productId)
			.orElseThrow(ProductNotFoundException::new);

		Admin admin = product.getAdmin();

		return ProductDetailResponse.from(product, admin);
	}
}