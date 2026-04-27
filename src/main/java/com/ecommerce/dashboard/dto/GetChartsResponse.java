package com.ecommerce.dashboard.dto;

import java.util.List;
import java.util.Map;
import java.util.PrimitiveIterator;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.ecommerce.common.enums.UserStatus;
import com.ecommerce.products.entity.Product;
import com.ecommerce.review.entity.Review;
import com.ecommerce.users.entity.User;

import lombok.Getter;

@Getter
public class GetChartsResponse {

	private final Long oneStarCount;
	private final Long twoStarCount;
	private final Long threeStarCount;
	private final Long fourStarCount;
	private final Long fiveStarCount;
	private final Long activeUsers;
	private final Long inactiveUsers;
	private final Long suspendedUsers;
	private final Map<String, Long> categoryCount;

	private GetChartsResponse(Long oneStarCount, Long twoStarCount, Long threeStarCount, Long fourStarCount, Long fiveStarCount,
		Long activeUsers, Long inactiveUsers, Long suspendedUsers, Map<String, Long> categoryCount) {
		this.oneStarCount = oneStarCount;
		this.twoStarCount = twoStarCount;
		this.threeStarCount = threeStarCount;
		this.fourStarCount = fourStarCount;
		this.fiveStarCount = fiveStarCount;
		this.activeUsers = activeUsers;
		this.inactiveUsers = inactiveUsers;
		this.suspendedUsers = suspendedUsers;
		this.categoryCount = categoryCount;
	}

	public static GetChartsResponse from(List<Review> allReviews, List<User> allUsers, List<Product> allProducts) {
		return new GetChartsResponse(

			allReviews.stream().filter(a -> a.getRating() == 1).count(),
			allReviews.stream().filter(a -> a.getRating() == 2).count(),
			allReviews.stream().filter(a -> a.getRating() == 3).count(),
			allReviews.stream().filter(a -> a.getRating() == 4).count(),
			allReviews.stream().filter(a -> a.getRating() == 5).count(),

			allUsers.stream().filter(a -> UserStatus.ACTIVE.equals(a.getStatus())).count(),
			allUsers.stream().filter(a -> UserStatus.INACTIVE.equals(a.getStatus())).count(),
			allUsers.stream().filter(a -> UserStatus.SUSPENDED.equals(a.getStatus())).count(),

			allProducts.stream().collect(Collectors.groupingBy(Product::getCategory, Collectors.counting()))
		);
	}

}
