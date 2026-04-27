package com.ecommerce.orders.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.common.enums.OrderStatus;
import com.ecommerce.orders.entity.Order;
import com.ecommerce.users.entity.User;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	//특정 유저 주문 갯수 조회
	long countByUser(User user);

	@Query("""
		SELECT o FROM Order o
		JOIN o.user u
		JOIN o.product p
		JOIN o.admin a
		WHERE o.admin.adminId = :adminId
		AND (
		    :keyword IS NULL OR :keyword = '' OR
		    o.number LIKE %:keyword% OR
		    u.name LIKE %:keyword%
		)
		AND (
		    :status IS NULL OR o.status = :status
		)
		""")
	Page<Order> searchOrders(
		@Param("adminId") Long adminId,
		@Param("keyword") String keyword,
		@Param("status") OrderStatus status,
		Pageable pageable
	);
}
