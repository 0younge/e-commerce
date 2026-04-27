package com.ecommerce.products.dto.response;

import java.util.Map;

/**
 * 리뷰 통계 조회 응답
 */
public record GetReviewStatistics (

	Double avg,
	Long total,
	Map<Integer, Long> counts ){


	public static GetReviewStatistics of(
		Double avg,
		Long total,
		Map<Integer, Long> counts
	) {
		Double result = Math.round(avg * 10) / 10.0;

		return new GetReviewStatistics(
			result,
			total,
			counts
		);
	}
}



