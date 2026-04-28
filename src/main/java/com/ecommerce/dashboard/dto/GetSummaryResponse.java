package com.ecommerce.dashboard.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;

@Getter
@JsonPropertyOrder({"totalAdmins", "activeAdmins", "totalUsers", "activeUsers", "shortageProducts", "totalOrders", "todayOrders", "totalReview", "avgRating"})
public class GetSummaryResponse {

	private final Long totalAdmins;
	private final Long activeAdmins;
	private final Long totalUsers;
	private final Long activeUsers;
	private final Long totalProducts;
	private final Long shortageProducts;
	private final Long totalOrders;
	private final Long todayOrders;
	private final Long totalReview;
	private final double avgRating;

	public GetSummaryResponse(Long totalAdmins, Long activeAdmins, Long totalUsers, Long activeUsers,
		Long totalProducts, Long shortageProducts, Long totalOrders, Long todayOrders, Long totalReview,
		double avgRating) {
		this.totalAdmins = totalAdmins;
		this.activeAdmins = activeAdmins;
		this.totalUsers = totalUsers;
		this.activeUsers = activeUsers;
		this.totalProducts = totalProducts;
		this.shortageProducts = shortageProducts;
		this.totalOrders = totalOrders;
		this.todayOrders = todayOrders;
		this.totalReview = totalReview;
		this.avgRating = avgRating;
	}

}
