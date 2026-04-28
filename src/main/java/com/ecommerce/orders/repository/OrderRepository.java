package com.ecommerce.orders.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.common.enums.OrderStatus;
import com.ecommerce.dashboard.dto.RecentOrderDto;
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
		    LEFT JOIN o.admin a
		    WHERE (
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

	@Query("SELECT COUNT(o) FROM Order o WHERE DATE(o.createdAt) = :date")
	long countByDate(@Param("date") LocalDate date);

	@Query("SELECT COALESCE(SUM(o.totalPrice), 0) FROM Order o")
	long sumTotalPrice();

	@Query("SELECT COALESCE(SUM(o.totalPrice), 0) FROM Order o WHERE DATE(o.createdAt) = :date")
	long sumTotalPriceByDate(@Param("date") LocalDate date);

	@Query("SELECT COUNT(o) FROM Order o WHERE o.status = :status")
	long countByStatus(@Param("status") OrderStatus status);

	@Query("""
    SELECT new com.ecommerce.dashboard.dto.RecentOrderDto(
        o.orderId,
        u.name,
        p.name,
        o.totalPrice,
        o.status
    )
    FROM Order o
    JOIN o.user u
    JOIN o.product p
    ORDER BY o.createdAt DESC
    LIMIT 10
    """)
	List<RecentOrderDto> findRecentTenOrders();

}
