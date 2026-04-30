package com.ecommerce.dashboard.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.common.response.ApiResponse;
import com.ecommerce.common.security.auth.SecurityAdminInfo;
import com.ecommerce.dashboard.dto.GetChartsResponse;
import com.ecommerce.dashboard.dto.GetRecentOrderResponse;
import com.ecommerce.dashboard.service.DashboardService;
import com.ecommerce.dashboard.dto.GetSummaryResponse;
import com.ecommerce.dashboard.dto.GetWidgetsResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class DashboardController {

	private final DashboardService dashboardService;

	/**
	 * Summary 통계 조회
	 * @param loginAdmin 검증을 위한 jwt값
	 * @return Summary 통계 데이터
	 */
	@PreAuthorize("hasRole('SUPER_ADMIN')")
	@GetMapping("/summary")
	public ResponseEntity<ApiResponse<GetSummaryResponse>> getSummary(
		@AuthenticationPrincipal SecurityAdminInfo loginAdmin) {
		return ResponseEntity.ok(ApiResponse.success("Summary 통계 조회 성공", dashboardService.getSummary(loginAdmin)));
	}

	/**
	 * Widgets 데이터 조회
	 * @param loginAdmin 검증을 위한 jwt값
	 * @return Widgets 총 데이터
	 */
	@PreAuthorize("hasRole('SUPER_ADMIN')")
	@GetMapping("/widgets")
	public ResponseEntity<ApiResponse<GetWidgetsResponse>> getWidgets(
		@AuthenticationPrincipal SecurityAdminInfo loginAdmin) {
		return ResponseEntity.ok(ApiResponse.success("Widgets 데이터 조회 성공", dashboardService.getWidgets(loginAdmin)));
	}

	/**
	 * Charts 데이터 조회
	 * @param loginAdmin 검증을 위한 jwt값
	 * @return Charts 총 데이터 조회
	 */
	@PreAuthorize("hasRole('SUPER_ADMIN')")
	@GetMapping("/charts")
	public ResponseEntity<ApiResponse<GetChartsResponse>> getCharts(
		@AuthenticationPrincipal SecurityAdminInfo loginAdmin) {
		return ResponseEntity.ok(ApiResponse.success("Charts 데이터 조회 성공", dashboardService.getCharts(loginAdmin)));
	}

	/**
	 * 최근 주문 조회
	 * @param loginAdmin 검증을 위한 jwt값
	 * @return 최근 10건의 주문 상세 조회
	 */
	@PreAuthorize("hasRole('SUPER_ADMIN')")
	@GetMapping("/recentorders")
	public ResponseEntity<ApiResponse<GetRecentOrderResponse>> getRecentOrders(
		@AuthenticationPrincipal SecurityAdminInfo loginAdmin) {
		return ResponseEntity.ok(ApiResponse.success("최근 주문 목록 조회 성공", dashboardService.getRecentOrders(loginAdmin)));
	}

}
