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
import com.ecommerce.dashboard.service.DashboardService;
import com.ecommerce.dashboard.dto.GetSummaryResponse;
import com.ecommerce.dashboard.dto.GetWidgetsResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class DashboardController {

	private final DashboardService dashboardService;

	@GetMapping("/summary")
	public ResponseEntity<ApiResponse<GetSummaryResponse>> getSummary(
		@SessionAttribute(name = AdminConst.ADMIN_INFO) AdminInfo adminInfo) {
		return ResponseEntity.ok(ApiResponse.success("Summary 통계 조회 성공", dashboardService.getSummary(adminInfo)));
	}

	@GetMapping("/widgets")
	public ResponseEntity<ApiResponse<GetWidgetsResponse>> getWidgets(
		@SessionAttribute(name = AdminConst.ADMIN_INFO) AdminInfo adminInfo) {
		return ResponseEntity.ok(ApiResponse.success("Widgets 데이터 조회 성공", dashboardService.getWidgets(adminInfo)));
	}

	@GetMapping("/charts")
	public ResponseEntity<ApiResponse<GetChartsResponse>> getCharts(
		@SessionAttribute(name = AdminConst.ADMIN_INFO) AdminInfo adminInfo) {
		return ResponseEntity.ok(ApiResponse.success("Charts 데이터 조회 성공", dashboardService.getCharts(adminInfo)));
	}

}
