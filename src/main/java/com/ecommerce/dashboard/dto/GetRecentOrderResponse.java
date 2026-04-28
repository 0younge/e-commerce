package com.ecommerce.dashboard.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class GetRecentOrderResponse {

	private final List<RecentOrderDto> orders;

	public GetRecentOrderResponse(List<RecentOrderDto> orders) {
		this.orders = orders;
	}
}
