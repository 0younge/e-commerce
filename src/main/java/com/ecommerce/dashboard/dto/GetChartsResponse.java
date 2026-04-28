package com.ecommerce.dashboard.dto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ecommerce.common.enums.UserStatus;
import com.ecommerce.products.entity.Product;
import com.ecommerce.review.entity.Review;
import com.ecommerce.users.entity.User;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;

@Getter
@JsonPropertyOrder({"oneStarCount", "twoStarCount", "threeStarCount", "fourStarCount", "fiveStarCount", "activeUsers", "inactiveUsers", "suspendedUsers", "categoryCount"})
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

	public GetChartsResponse(Long oneStarCount, Long twoStarCount, Long threeStarCount, Long fourStarCount, Long fiveStarCount,
		Long activeUsers, Long inactiveUsers, Long suspendedUsers, List<CategoryCountDto> categoryCount) {
		this.oneStarCount = oneStarCount;
		this.twoStarCount = twoStarCount;
		this.threeStarCount = threeStarCount;
		this.fourStarCount = fourStarCount;
		this.fiveStarCount = fiveStarCount;
		this.activeUsers = activeUsers;
		this.inactiveUsers = inactiveUsers;
		this.suspendedUsers = suspendedUsers;
		this.categoryCount = categoryCount.stream()
			.collect(Collectors.toMap(
				CategoryCountDto::getCategory,
				CategoryCountDto::getCount
			));
	}

}
