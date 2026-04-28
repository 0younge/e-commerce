package com.ecommerce.dashboard.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ecommerce.orders.entity.Order;

import lombok.Getter;

@Getter
public class GetRecentOrderResponse {

	private final List<Map<String, Object>> orders;

	private GetRecentOrderResponse(List<Map<String, Object>> orders) {
		this.orders = orders;
	}

	public static GetRecentOrderResponse from(List<Order> recentTenOrders) {
		return new GetRecentOrderResponse(
			recentTenOrders.stream()
				.map(a -> {
					Map<String, Object> map = new HashMap<>();
					map.put("orderId", a.getOrderId());
					map.put("userName", a.getUser().getName());
					map.put("productName", a.getProduct().getName());
					map.put("price", a.getTotalPrice());
					map.put("status", a.getStatus().name());
					return map;
				})
				.toList()
		);
	}
}
