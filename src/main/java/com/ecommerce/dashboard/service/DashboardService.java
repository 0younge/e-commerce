package com.ecommerce.dashboard.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.admins.entity.Admin;
import com.ecommerce.admins.repository.AdminRepository;
import com.ecommerce.common.enums.AdminStatus;
import com.ecommerce.common.enums.OrderStatus;
import com.ecommerce.common.enums.UserStatus;
import com.ecommerce.common.exception.AdminNotFoundException;
import com.ecommerce.common.exception.AdminStatusException;
import com.ecommerce.common.security.auth.SecurityAdminInfo;
import com.ecommerce.dashboard.dto.GetChartsResponse;
import com.ecommerce.dashboard.dto.GetRecentOrderResponse;
import com.ecommerce.dashboard.dto.GetSummaryResponse;
import com.ecommerce.dashboard.dto.GetWidgetsResponse;
import com.ecommerce.orders.repository.OrderRepository;
import com.ecommerce.products.repository.ProductRepository;
import com.ecommerce.review.repository.ReviewRepository;
import com.ecommerce.users.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardService {

	private final AdminRepository adminRepository;
	private final UserRepository userRepository;
	private final ProductRepository productRepository;
	private final OrderRepository orderRepository;
	private final ReviewRepository reviewRepository;

	@Transactional(readOnly = true)
	public GetSummaryResponse getSummary(SecurityAdminInfo loginAdmin) {
		findByIdOrThrow(loginAdmin);
		LocalDate today = LocalDate.now();

		return GetSummaryResponse.from(
			adminRepository.count(),
			adminRepository.countByStatus(AdminStatus.ACTIVE),

			userRepository.count(),
			userRepository.countByStatus(UserStatus.ACTIVE),

			productRepository.count(),
			productRepository.countLowStock(5),

			orderRepository.count(),
			orderRepository.countByDate(today),

			reviewRepository.count(),
			Optional.ofNullable(reviewRepository.findAverageRating()).orElse(0.0)
		);
	}

	@Transactional(readOnly = true)
	public GetWidgetsResponse getWidgets(SecurityAdminInfo loginAdmin) {
		findByIdOrThrow(loginAdmin);
		LocalDate today = LocalDate.now();

		return GetWidgetsResponse.from(
			orderRepository.sumTotalPrice(),
			orderRepository.sumTotalPriceByDate(today),

			orderRepository.countByStatus(OrderStatus.READY),
			orderRepository.countByStatus(OrderStatus.SHIPPING),
			orderRepository.countByStatus(OrderStatus.DELIVERED),

			productRepository.countLowStock(5),
			productRepository.countOutOfStock()
		);
	}

	@Transactional(readOnly = true)
	public GetChartsResponse getCharts(SecurityAdminInfo loginAdmin) {
		findByIdOrThrow(loginAdmin);

		return GetChartsResponse.from(
			reviewRepository.countByRating(1),
			reviewRepository.countByRating(2),
			reviewRepository.countByRating(3),
			reviewRepository.countByRating(4),
			reviewRepository.countByRating(5),

			userRepository.countByStatus(UserStatus.ACTIVE),
			userRepository.countByStatus(UserStatus.INACTIVE),
			userRepository.countByStatus(UserStatus.SUSPENDED),

			productRepository.countGroupByCategory()
		);
	}

	@Transactional(readOnly = true)
	public GetRecentOrderResponse getRecentOrders(SecurityAdminInfo loginAdmin) {
		findByIdOrThrow(loginAdmin);

		return GetRecentOrderResponse.from(orderRepository.findRecentTenOrders());
	}

	public void findByIdOrThrow(SecurityAdminInfo loginAdmin) {
		Admin admin = adminRepository.findById(loginAdmin.adminId())
			.orElseThrow(() -> new AdminNotFoundException("존재하지 않는 유저입니다."));
		if (!admin.getStatus().equals(AdminStatus.ACTIVE)) {
			throw new AdminStatusException(HttpStatus.FORBIDDEN, "활성 상태 관리자만 접근할 수 있습니다.");
		}
	}
}
