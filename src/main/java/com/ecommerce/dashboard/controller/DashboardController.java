package com.ecommerce.dashboard.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.ecommerce.admins.entity.AdminConst;
import com.ecommerce.admins.entity.AdminInfo;
import com.ecommerce.common.response.ApiResponse;
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
	 * @param adminInfo 검증을 위한 세션값
	 * @return Summary 통계 데이터
	 */
	@GetMapping("/summary")
	public ResponseEntity<ApiResponse<GetSummaryResponse>> getSummary(
		@SessionAttribute(name = AdminConst.ADMIN_INFO) AdminInfo adminInfo) {
		return ResponseEntity.ok(ApiResponse.success("Summary 통계 조회 성공", dashboardService.getSummary(adminInfo)));
	}

	/**
	 * Widgets 데이터 조회
	 * @param adminInfo 검증을 위한 세션값
	 * @return Widgets 총 데이터
	 */
	@GetMapping("/widgets")
	public ResponseEntity<ApiResponse<GetWidgetsResponse>> getWidgets(
		@SessionAttribute(name = AdminConst.ADMIN_INFO) AdminInfo adminInfo) {
		return ResponseEntity.ok(ApiResponse.success("Widgets 데이터 조회 성공", dashboardService.getWidgets(adminInfo)));
	}

	/**
	 * Charts 데이터 조회
	 * @param adminInfo 검증을 위한 세션값
	 * @return Charts 총 데이터 조회
	 */
	@GetMapping("/charts")
	public ResponseEntity<ApiResponse<GetChartsResponse>> getCharts(
		@SessionAttribute(name = AdminConst.ADMIN_INFO) AdminInfo adminInfo) {
		return ResponseEntity.ok(ApiResponse.success("Charts 데이터 조회 성공", dashboardService.getCharts(adminInfo)));
	}

	/**
	 * 최근 주문 조회
	 * @param adminInfo 검증을 위한 세션값
	 * @return 최근 10건의 주문 상세 조회
	 */
	@GetMapping("/recentoders")
	public ResponseEntity<ApiResponse<GetRecentOrderResponse>> getRecentOrders(
		@SessionAttribute(name = AdminConst.ADMIN_INFO) AdminInfo adminInfo) {
		return ResponseEntity.ok(ApiResponse.success("최근 주문 목록 조회 성공", dashboardService.getRecentOrders(adminInfo)));
	}

}
