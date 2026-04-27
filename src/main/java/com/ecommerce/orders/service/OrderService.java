package com.ecommerce.orders.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.admins.entity.Admin;
import com.ecommerce.admins.repository.AdminRepository;
import com.ecommerce.common.enums.OrderStatus;
import com.ecommerce.common.exception.InvalidRequestException;
import com.ecommerce.common.exception.OrderNotFoundException;
import com.ecommerce.common.exception.ProductNotFoundException;
import com.ecommerce.common.exception.UserNotFoundException;
import com.ecommerce.orders.dto.CreateOrderRequest;
import com.ecommerce.orders.dto.CreateOrderResponse;
import com.ecommerce.orders.dto.GetOrderAllResponse;
import com.ecommerce.orders.dto.GetOrderOneResponse;
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
	 * @param request 요청body
	 * @return 응답body
	 */
	@Transactional
	public CreateOrderResponse save(CreateOrderRequest request, Long adminId) {
		User user = userRepository.findById(request.getUserId()).orElseThrow(
			UserNotFoundException::new
		);
		Product product = productRepository.findById(request.getProductId()).orElseThrow(
			ProductNotFoundException::new
		);

		//주문 수량만큼 상품 재고 검증 및 차감 처리 - Product클래스에서 구현 필요
		// product.decreaseStock(request.getQuantity());
		//재고 변경에 따른 상품 상태 자동 전환 처리 - Product클래스에서 구현 필요

		//주문 번호 생성 및 총 가격 계산
		String orderNumber = generateOrderNumber(user);
		Long totalPrice = product.getPrice() * request.getQuantity();

		Order order = new Order(orderNumber, request.getQuantity(), totalPrice, user, product);

		// 유저주문과 관리자 주문 구분
		if (adminId != null) {
			Admin admin = adminRepository.findById(adminId).orElseThrow(
				() -> new IllegalStateException("관리자 로그인이 팔요합니다."));
			order.assignAdmin(admin);
		}

		Order savedOrder = orderRepository.save(order);

		return new CreateOrderResponse(
			savedOrder.getOrderId(),
			savedOrder.getNumber(),
			savedOrder.getUser().getUserId(),
			savedOrder.getProduct().getProductId(),
			adminId,
			savedOrder.getQuantity(),
			savedOrder.getTotalPrice(),
			savedOrder.getStatus(),
			savedOrder.getCreatedAt()
		);
	}

	/**
	 *
	 * @param user
	 * @return
	 */
	private String generateOrderNumber(User user) {
		String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

		long orderCount = orderRepository.countByUser(user);
		long nextOrderNumber = orderCount + 1;

		return String.format("%s_%d_%d", datePart, user.getUserId(), nextOrderNumber);
	}

	@Transactional(readOnly = true)
	public Page<GetOrderAllResponse> getAll(
		Long adminId,
		String keyword, int page, int size, String sortBy, String sortOrder, OrderStatus status) {

		//1. 정렬 방향
		Sort.Direction direction = sortOrder.equalsIgnoreCase("asc")
			? Sort.Direction.ASC
			: Sort.Direction.DESC;

		//2. sortBy 안전 처리 (화이트리스트)
		if (!List.of("quantity", "totalPrice", "createdAt").contains(sortBy)) {
			sortBy = "createdAt";
		}

		//3. Pageable 생성 (중요: page-1)
		Pageable pageable = PageRequest.of(page - 1, size, Sort.by(direction, sortBy));

		//4. 조회
		Page<Order> orderPage = orderRepository.searchOrders(
			adminId,
			keyword,
			status,
			pageable
		);

		return orderPage.map(order -> new GetOrderAllResponse(
			order.getOrderId(),
			order.getNumber(),
			order.getUser().getName(),
			order.getProduct().getName(),
			order.getQuantity(),
			order.getTotalPrice(),
			order.getStatus(),
			order.getAdmin().getName()
		));
	}

	@Transactional(readOnly = true)
	public GetOrderOneResponse getOne(Long orderId) {
		Order order = orderRepository.findById(orderId).orElseThrow(
			OrderNotFoundException::new
		);
		return new GetOrderOneResponse(
			order.getNumber(),
			order.getUser().getName(),
			order.getUser().getEmail(),
			order.getProduct().getName(),
			order.getQuantity(),
			order.getTotalPrice(),
			order.getCreatedAt(),
			order.getStatus(),
			order.getAdmin().getName(),
			order.getAdmin().getEmail(),
			order.getAdmin().getRole()
		);
	}

	@Transactional
	public void updateStatus(Long orderId, OrderStatus nextStatus) {
		Order order = orderRepository.findById(orderId).orElseThrow(
			OrderNotFoundException::new
		);
		OrderStatus currentStatus = order.getStatus();
		if (currentStatus == OrderStatus.CANCELED) {
			throw new InvalidRequestException("취소된 주문은 상태 변경 불가합니다.");
		}
		if (currentStatus == OrderStatus.READY) {
			if (nextStatus != OrderStatus.SHIPPING && nextStatus != OrderStatus.CANCELED) {
				throw new InvalidRequestException("준비 중 단계에서는 배송 시작이나 취소만 가능합니다.");
			}
		} else if (currentStatus == OrderStatus.SHIPPING) {
			if (nextStatus != OrderStatus.DELIVERED) {
				throw new InvalidRequestException("배송 중 단계에서는 배송 완료만 가능합니다.");
			}
		} else if (currentStatus == OrderStatus.DELIVERED) {
			if (nextStatus != OrderStatus.DELIVERED) {
				throw new InvalidRequestException("이미 배송 완료된 주문은 수정할 수 없습니다.");
			}
		}
		/*
		관리자페이지에서 배송 시작 버튼 누르면 배송중으로 변경되어야함
		 */
		order.updateStatus(nextStatus);
	}

	/**
	 *
	 * @param orderId
	 * @param cancelReason
	 */
	@Transactional
	public void cancelOrder(Long orderId, String cancelReason) {
		Order order = orderRepository.findById(orderId).orElseThrow(
			OrderNotFoundException::new
		);
		order.cancel(cancelReason);

		//재고 복구 로직 추가 필요 product랑 협의.
		Product product = order.getProduct();
	}
}
