package com.ecommerce.review.dto;

import lombok.Getter;

@Getter
public class ReviewStats {
	private final double ratingAverage;
	private final int totalReviewsCount;
	private final int ratingCount1;
	private final int ratingCount2;
	private final int ratingCount3;
	private final int ratingCount4;
	private final int ratingCount5;

	public ReviewStats(double ratingAverage, int totalReviewsCount, int ratingCount1, int ratingCount2,
		int ratingCount3, int ratingCount4, int ratingCount5) {
		this.ratingAverage = ratingAverage;
		this.totalReviewsCount = totalReviewsCount;
		this.ratingCount1 = ratingCount1;
		this.ratingCount2 = ratingCount2;
		this.ratingCount3 = ratingCount3;
		this.ratingCount4 = ratingCount4;
		this.ratingCount5 = ratingCount5;
	}
}
