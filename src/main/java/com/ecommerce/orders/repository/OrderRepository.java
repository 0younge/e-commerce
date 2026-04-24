package com.ecommerce.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.orders.entity.Order;
import com.ecommerce.users.entity.User;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	//특정 유저 주문 갯수 조회
	long countByUser(User user);
}
