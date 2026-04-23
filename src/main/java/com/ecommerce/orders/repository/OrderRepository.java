package com.ecommerce.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.orders.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
