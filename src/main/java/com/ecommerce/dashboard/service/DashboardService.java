package com.ecommerce.dashboard.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.admins.entity.Admin;
import com.ecommerce.admins.entity.AdminInfo;
import com.ecommerce.admins.repository.AdminRepository;
import com.ecommerce.common.enums.AdminStatus;
import com.ecommerce.common.enums.OrderStatus;
import com.ecommerce.common.enums.UserStatus;
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
	public GetSummaryResponse getSummary(AdminInfo adminInfo) {
		findByIdOrThrow(adminInfo);
		LocalDate today = LocalDate.now();

		return new GetSummaryResponse(
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
	public GetWidgetsResponse getWidgets(AdminInfo adminInfo) {
		findByIdOrThrow(adminInfo);
		LocalDate today = LocalDate.now();

		return new GetWidgetsResponse(
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
	public GetChartsResponse getCharts(AdminInfo adminInfo) {
		findByIdOrThrow(adminInfo);

		return new GetChartsResponse(
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
	public GetRecentOrderResponse getRecentOrders(AdminInfo adminInfo) {
		findByIdOrThrow(adminInfo);

		return new GetRecentOrderResponse(orderRepository.findRecentTenOrders());
	}

	public void findByIdOrThrow(AdminInfo adminInfo) {
		Admin admin = adminRepository.findById(adminInfo.getAdminId())
			.orElseThrow(() -> new IllegalArgumentException("나중에 수정할 예외"));
		if (!admin.getStatus().equals(AdminStatus.ACTIVE)) {
			throw new IllegalStateException("권한이 없습니다");
		}
	}
}
