package com.ecommerce.orders.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.admins.entity.Admin;
import com.ecommerce.admins.repository.AdminRepository;
import com.ecommerce.common.enums.OrderStatus;
import com.ecommerce.common.exception.AdminLoginStatusException;
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

		//주문 수량만큼 상품 재고 검증 및 차감 처리 & 상품 상태 변경
		product.decreaseQuantity(request.getQuantity());

		//주문 번호 생성 및 총 가격 계산
		// TODO: 동시성 문제 수정
		String orderNumber = generateOrderNumber(user);
		Long totalPrice = product.getPrice() * request.getQuantity();

		Order order = new Order(orderNumber, request.getQuantity(), totalPrice, user, product);

		// 유저주문과 관리자 주문 구분
		if (adminId != null) {
			Admin admin = adminRepository.findById(adminId).orElseThrow(
				AdminLoginStatusException::new
			);
			order.assignAdmin(admin);
		}

		Order savedOrder = orderRepository.save(order);

		return CreateOrderResponse.from(savedOrder, adminId);
	}

	/**
	 * 주문 번호 생성 (주문생성날짜_유저id_주문번호)
	 * @param user 유저 번호
	 * @return 주문번호
	 */
	private String generateOrderNumber(User user) {
		String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

		long orderCount = orderRepository.countByUser(user);
		long nextOrderNumber = orderCount + 1;

		return String.format("%s_%d_%d", datePart, user.getUserId(), nextOrderNumber);
	}

	/**
	 * 주문 리스트 조회 - 관리자 로그인시에만 접근 가능
	 * @param keyword 검색할 키워드
	 * @param page 페이지 번호
	 * @param size 페이지당 개수
	 * @param sortBy 정렬 기준
	 * @param sortOrder 정렬 순서
	 * @param status 검색할 상태
	 * @return 페이지네이션을 마친 주문 리스트
	 */
	@Transactional(readOnly = true)
	public Page<GetOrderAllResponse> getAll(String keyword, int page, int size, String sortBy, String sortOrder, OrderStatus status) {

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
			keyword,
			status,
			pageable
		);

		return orderPage.map(GetOrderAllResponse::from);
	}

	/**
	 * 특정 주문 조회 - 관리자 로그인시에만 접근 가능
	 *
	 * @param orderId 주문 고유 id
	 * @return 특정 주문의 상세 정보
	 */
	@Transactional(readOnly = true)
	public GetOrderOneResponse getOne(Long orderId) {
		Order order = orderRepository.findById(orderId).orElseThrow(
			OrderNotFoundException::new
		);
		return GetOrderOneResponse.from(order);
	}

	/**
	 * 주문 상태 수정 - 관리자 로그인시에만 접근 가능
	 *
	 * @param orderId    주문 고유 id
	 * @param nextStatus 다음 상태
	 */
	@Transactional
	public void updateStatus(Long orderId, OrderStatus nextStatus) {
		Order order = orderRepository.findById(orderId).orElseThrow(
			OrderNotFoundException::new
		);
		order.changeStatus(nextStatus);
	}

	/**
	 * 주문 취소
	 * @param orderId 주문 고유 id
	 * @param cancelReason 취소 사유
	 */
	@Transactional
	public void cancelOrder(Long orderId, String cancelReason) {
		Order order = orderRepository.findById(orderId).orElseThrow(
			OrderNotFoundException::new
		);
		order.cancel(cancelReason);

		Product product = order.getProduct();
		product.increaseQuantity(order.getQuantity());
	}
}
