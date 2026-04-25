package com.ecommerce.orders.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.admins.entity.Admin;
import com.ecommerce.admins.entity.AdminConst;
import com.ecommerce.admins.entity.AdminInfo;
import com.ecommerce.orders.dto.CreateOrderRequest;
import com.ecommerce.orders.dto.CreateOrderResponse;
import com.ecommerce.orders.service.OrderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@PostMapping
	public ResponseEntity<CreateOrderResponse> saveOrder(
		@RequestBody CreateOrderRequest request,
		@SessionAttribute(name = AdminConst.ADMIN_INFO, required = false) AdminInfo adminInfo
	) {

		log.info("컨트롤러 호출");
		if (adminInfo == null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "관리자 로그인이 필요합니다.");
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(orderService.save(request, adminInfo.getAdminId()));
	}
}
