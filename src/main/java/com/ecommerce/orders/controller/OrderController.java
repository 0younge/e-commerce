package com.ecommerce.orders.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.admins.entity.AdminConst;
import com.ecommerce.admins.entity.AdminInfo;
import com.ecommerce.common.enums.OrderStatus;
import com.ecommerce.common.exception.AdminLoginStatusException;
import com.ecommerce.common.response.ApiResponse;
import com.ecommerce.common.security.auth.SecurityAdminInfo;
import com.ecommerce.orders.dto.CancelOrderRequest;
import com.ecommerce.orders.dto.CreateOrderRequest;
import com.ecommerce.orders.dto.CreateOrderResponse;
import com.ecommerce.orders.dto.GetOrderAllResponse;
import com.ecommerce.orders.dto.GetOrderOneResponse;
import com.ecommerce.orders.dto.UpdateOrderStatusRequest;
import com.ecommerce.orders.service.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@PostMapping
	public ResponseEntity<ApiResponse<CreateOrderResponse>> createAdminOrder(
		@Valid @RequestBody CreateOrderRequest request,
		@AuthenticationPrincipal SecurityAdminInfo loginAdmin
	) {
		Long adminId = (loginAdmin != null) ? loginAdmin.adminId() : null;

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.created("주문이 성공적으로 생성되었습니다.", orderService.save(request, adminId)));
	}

	@GetMapping
	public ResponseEntity<ApiResponse<Page<GetOrderAllResponse>>> getOrders(
		@RequestParam(defaultValue = "") String keyword,
		@RequestParam(defaultValue = "1") int page,
		@RequestParam(defaultValue = "10") int size,
		@RequestParam(defaultValue = "createdAt") String sortBy,
		@RequestParam(defaultValue = "desc") String sortOrder,
		@RequestParam(required = false) OrderStatus status
	) {
		Page<GetOrderAllResponse> response = orderService.getAll(
			keyword,
			page,
			size,
			sortBy,
			sortOrder,
			status
		);
		return ResponseEntity.ok()
			.body(ApiResponse.success("주문 리스트가 성공적으로 조회되었습니다.", response));
	}

	@GetMapping("/{orderId}")
	public ResponseEntity<ApiResponse<GetOrderOneResponse>> getOrder(
		@PathVariable Long orderId
	) {
		GetOrderOneResponse response = orderService.getOne(orderId);
		return ResponseEntity.ok()
			.body(ApiResponse.success("주문 상세 조회가 성공적으로 조회되었습니다.", response));
	}

	@PatchMapping("/{orderId}")
	public ResponseEntity<ApiResponse<Void>> updateOrderStatus(
		@PathVariable Long orderId,
		@RequestBody UpdateOrderStatusRequest request
	) {
		orderService.updateStatus(orderId, request.getStatus());
		return ResponseEntity.ok(ApiResponse.success("주문 상태가 변경되었습니다."));
	}

	@PatchMapping("/{orderId}/cancel")
	public ResponseEntity<ApiResponse<Void>> cancelOrder(
		@PathVariable Long orderId,
		@Valid @RequestBody CancelOrderRequest request
	) {
		orderService.cancelOrder(orderId, request.getCancelReason());
		return ResponseEntity.ok(ApiResponse.success("주문이 취소되었습니다."));
	}
}
