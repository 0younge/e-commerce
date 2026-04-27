package com.ecommerce.dashboard;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.ecommerce.admins.entity.AdminConst;
import com.ecommerce.admins.entity.AdminInfo;
import com.ecommerce.common.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class DashboardController {

	private final DashboardService dashboardService;

	@GetMapping("/summary")
	public ResponseEntity<ApiResponse<GetSummaryResponse>> getDashboard(
		@SessionAttribute(name = AdminConst.ADMIN_INFO, required = true) AdminInfo adminInfo) {
		return ResponseEntity.ok(ApiResponse.success("대쉬보드 조회 성공", dashboardService.getSummary(adminInfo)));
	}

}
