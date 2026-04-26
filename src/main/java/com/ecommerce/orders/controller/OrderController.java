package com.ecommerce.orders.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.orders.dto.CreateOrderRequest;
import com.ecommerce.orders.dto.CreateOrderResponse;
import com.ecommerce.orders.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@PostMapping
	public ResponseEntity<CreateOrderResponse> saveOrder(@RequestBody CreateOrderRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(orderService.save(request));
	}
}
