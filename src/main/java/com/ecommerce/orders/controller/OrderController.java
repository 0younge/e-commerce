package com.ecommerce.orders.controller;

import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.orders.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

}
