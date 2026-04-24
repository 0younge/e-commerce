package com.ecommerce.products.service;

import org.springframework.stereotype.Service;

import com.ecommerce.admins.entity.Admin;
import com.ecommerce.admins.repository.AdminRepository;
import com.ecommerce.common.exception.InvalidRequestException;
import com.ecommerce.common.exception.ProductNotFoundException;
import com.ecommerce.orders.service.OrderService;
import com.ecommerce.products.dto.ProductDetailResponse;
import com.ecommerce.products.dto.ProductRequest;
import com.ecommerce.products.dto.ProductResponse;
import com.ecommerce.products.entity.Product;
import com.ecommerce.products.repository.ProductRepository;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	private final AdminRepository adminRepository;
	private final OrderService orderService;

	/**
	 * 상품 등록
	 *
	 * @param request 상품 등록 요청 정보 (adminId, name, category, price, quantity)
	 * @return ProductResponse 등록된 상품 정보
	 * @throws IllegalArgumentException 관리자가 존재하지 않는 경우
	 */
	@Transactional
	public ProductResponse save(ProductRequest request) {

		// if (!adminRepository.existsById(request.getAdminId())) {
		// 	throw new InvalidRequestException(
		// 		"존재하지 않는 관리자입니다."
		// 	);
		// }

		Product newProduct = new Product(
			request.getAdminId(),
			request.getName(),
			request.getCategory(),
			request.getPrice(),
			request.getQuantity()
		);

		return ProductResponse.from(productRepository.save(newProduct));
	}

	//상품목록 죄회(검색 + 필터 + 페이징)


	/**
	 * 상품 상세 조회
	 * Product 정보 + Admin 정보 포함
	 *
	 * @param productId 상품 ID
	 * @return ProductDetailResponse 상품 상세 정보 (관리자 정보 포함)
	 * @throws ProductNotFoundException 상품을 찾을 수 없는 경우
	 * @throws InvalidRequestException 관리자 정보를 찾을 수 없는 경우
	 */
	@Transactional(readOnly = true)
	public ProductDetailResponse getProductDatall(Long productId) {

		Product product = productRepository.findById(productId)
			.orElseThrow(ProductNotFoundException::new);

		Admin admin = adminRepository.findById(product.getAdminId())
			.orElseThrow(() -> new InvalidRequestException("관리자을 찿을수 없습니다"));

		return ProductDetailResponse.from(product, admin);
	}

	//재고 변경 로직구현

	//상품 주문재고 처리

	//상품삭자

}
