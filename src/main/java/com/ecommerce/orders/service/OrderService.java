package com.ecommerce.orders.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.admins.entity.Admin;
import com.ecommerce.admins.repository.AdminRepository;
import com.ecommerce.common.exception.ProductNotFoundException;
import com.ecommerce.common.exception.UserNotFoundException;
import com.ecommerce.orders.dto.CreateOrderRequest;
import com.ecommerce.orders.dto.CreateOrderResponse;
import com.ecommerce.orders.entity.Order;
import com.ecommerce.orders.repository.OrderRepository;
import com.ecommerce.products.entity.Product;
import com.ecommerce.products.repository.ProductRepository;
import com.ecommerce.users.entity.User;
import com.ecommerce.users.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final UserRepository userRepository;
	private final ProductRepository productRepository;
	private final AdminRepository adminRepository;

	/**
	 * 주문 생성
	 *
	 * @param request
	 * @param adminId
	 * @return
	 */
	@Transactional
	public CreateOrderResponse save(CreateOrderRequest request, Long adminId) {
		User user = userRepository.findById(request.getUserId()).orElseThrow(
			UserNotFoundException::new
		);
		Product product = productRepository.findById(request.getProductId()).orElseThrow(
			ProductNotFoundException::new
		);
		Admin admin = adminRepository.findById(adminId).orElseThrow(
			() -> new IllegalStateException("관리자를 찾을 수 없습니다.")
		);

		//주문 수량만큼 상품 재고 검증 및 차감 처리 - Product클래스에서 구현 필요
		// product.decreaseStock(request.getQuantity());
		//재고 변경에 따른 상품 상태 자동 전환 처리 - Product클래스에서 구현 필요

		//주문 번호 생성 및 총 가격 계산
		String orderNumber = generateOrderNumber(user);
		Long totalPrice = product.getPrice() * request.getQuantity();

		Order order = new Order(orderNumber, request.getQuantity(), totalPrice, user, product, admin);
		Order savedOrder = orderRepository.save(order);

		return new CreateOrderResponse(
			savedOrder.getOrderId(),
			savedOrder.getNumber(),
			savedOrder.getUser().getUserId(),
			savedOrder.getProduct().getProductId(),
			savedOrder.getAdmin().getAdminId(),
			savedOrder.getQuantity(),
			savedOrder.getStatus(),
			savedOrder.getTotalPrice(),
			savedOrder.getCreatedAt()
		);
	}

	private String generateOrderNumber(User user) {
		String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

		long orderCount = orderRepository.countByUser(user);
		long nextOrderNumber = orderCount + 1;

		return String.format("%s_%d_%d", datePart, user.getUserId(), nextOrderNumber);
	}

}
