package com.ecommerce.dashboard;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.admins.entity.Admin;
import com.ecommerce.admins.entity.AdminInfo;
import com.ecommerce.admins.repository.AdminRepository;
import com.ecommerce.common.enums.AdminStatus;
import com.ecommerce.orders.entity.Order;
import com.ecommerce.orders.repository.OrderRepository;
import com.ecommerce.products.entity.Product;
import com.ecommerce.products.repository.ProductRepository;
import com.ecommerce.review.entity.Review;
import com.ecommerce.review.repository.ReviewRepository;
import com.ecommerce.users.entity.User;
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
		Admin admin = adminRepository.findById(adminInfo.getAdminId())
			.orElseThrow(() -> new IllegalArgumentException("나중에 수정할 예외"));
		if (!admin.getStatus().equals(AdminStatus.ACTIVE)) {
			throw new IllegalStateException("권한이 없습니다");
		}

		List<Admin> allAdmins = adminRepository.findAll();
		List<User> allUsers = userRepository.findAll();
		List<Product> allProducts = productRepository.findAll();
		List<Order> allOrders = orderRepository.findAll();
		List<Review> allReviews = reviewRepository.findAll();

		return GetSummaryResponse.from(allAdmins, allUsers, allProducts, allOrders, allReviews);
	}
}
