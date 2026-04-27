package com.ecommerce.dashboard;

import java.time.LocalDate;
import java.util.List;

import com.ecommerce.admins.entity.Admin;
import com.ecommerce.common.enums.AdminStatus;
import com.ecommerce.common.enums.UserStatus;
import com.ecommerce.orders.entity.Order;
import com.ecommerce.products.entity.Product;
import com.ecommerce.review.entity.Review;
import com.ecommerce.users.entity.User;
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

	private GetSummaryResponse(Long totalAdmins, Long activeAdmins, Long totalUsers, Long activeUsers,
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

	public static GetSummaryResponse from(List<Admin> allAdmins, List<User> allUsers, List<Product> allProducts,
		List<Order> allOrders, List<Review> allReviews) {
		LocalDate today = LocalDate.now();

		return new GetSummaryResponse(
			(long)allAdmins.size(),
			allAdmins.stream().filter(a -> AdminStatus.ACTIVE.equals(a.getStatus())).count(),

			(long)allUsers.size(),
			allUsers.stream().filter(a -> UserStatus.ACTIVE.equals(a.getStatus())).count(),

			(long)allProducts.size(),
			allProducts.stream().filter(a -> a.getQuantity() <= 5).count(),

			(long)allOrders.size(),
			allOrders.stream().filter(a -> a.getCreatedAt().toLocalDate().equals(today)).count(),

			(long)allReviews.size(),
			allReviews.stream().mapToDouble(a -> (double)a.getRating()).average().orElse(0.0));
	}
}
