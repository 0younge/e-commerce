package com.ecommerce.orders.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.admins.entity.AdminConst;
import com.ecommerce.admins.entity.AdminInfo;
import com.ecommerce.common.enums.OrderStatus;
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
	public ResponseEntity<CreateOrderResponse> saveOrder(@Valid @RequestBody CreateOrderRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(orderService.save(request, null));
	}

	@PostMapping("/admins")
	public ResponseEntity<CreateOrderResponse> createAdminOrder(
		@Valid @RequestBody CreateOrderRequest request,
		@SessionAttribute(name = AdminConst.ADMIN_INFO, required = false) AdminInfo adminInfo
	) {
		if (adminInfo == null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "관리자 로그인이 필요합니다.");
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(orderService.save(request, adminInfo.getAdminId()));
	}

	@GetMapping
	public ResponseEntity<Page<GetOrderAllResponse>> getOrders(
		@SessionAttribute(name = AdminConst.ADMIN_INFO, required = false) AdminInfo adminInfo,
		@RequestParam(defaultValue = "") String keyword,
		@RequestParam(defaultValue = "1") int page,
		@RequestParam(defaultValue = "10") int size,
		@RequestParam(defaultValue = "createdAt") String sortBy,
		@RequestParam(defaultValue = "desc") String sortOrder,
		@RequestParam(required = false) OrderStatus status
	) {
		if (adminInfo == null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "관리자 로그인이 필요합니다.");
		}
		return ResponseEntity.ok(
			orderService.getAll(
				adminInfo.getAdminId(),
				keyword,
				page,
				size,
				sortBy,
				sortOrder,
				status
			)
		);
	}

	@GetMapping("/{orderId}")
	public ResponseEntity<GetOrderOneResponse> getOrder(@PathVariable Long orderId) {
		return ResponseEntity.status(HttpStatus.OK).body(orderService.getOne(orderId));
	}

	@PatchMapping("/{orderId}")
	public ResponseEntity<Void> updateOrderStatus(
		@PathVariable Long orderId,
		@RequestBody UpdateOrderStatusRequest request) {
		orderService.updateStatus(orderId, request.getStatus());
		return ResponseEntity.ok().build();
	}

	@PatchMapping("/{orderId}/cancel")
	public ResponseEntity<Void> cancelOrder(
		@Valid
		@PathVariable Long orderId,
		@RequestBody CancelOrderRequest request
	) {
		if (request.getCancelReason() == null || request.getCancelReason().isBlank()){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "취소 사유는 필수입니다.");
		}
		orderService.cancelOrder(orderId, request.getCancelReason());
		return ResponseEntity.ok().build();
	}
}
