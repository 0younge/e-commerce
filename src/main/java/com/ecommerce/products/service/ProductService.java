package com.ecommerce.products.service;

import org.springframework.stereotype.Service;

import com.ecommerce.admins.entity.AdminRole;
import com.ecommerce.admins.repository.AdminRepository;
import com.ecommerce.common.exception.AccessDeniedException;
import com.ecommerce.common.exception.InvalidRequestException;
import com.ecommerce.orders.service.OrderService;
import com.ecommerce.products.dto.ProductRequest;
import com.ecommerce.products.dto.ProductResponse;
import com.ecommerce.products.entity.Product;
import com.ecommerce.products.repository.ProductRepository;

import jakarta.transaction.Transactional;
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

		if (!adminRepository.existsById(request.getAdminId())) {
			throw new InvalidRequestException(
				"존재하지 않는 관리자입니다."
			);
		}

		Product newProduct = new Product(
			request.getAdminId(),
			request.getName(),
			request.getCategory(),
			request.getPrice(),
			request.getQuantity()
		);

		return ProductResponse.from(productRepository.save(newProduct));
	}

}
