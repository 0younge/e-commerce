package com.ecommerce.orders.service;

import org.springframework.stereotype.Service;

import com.ecommerce.orders.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;

}
