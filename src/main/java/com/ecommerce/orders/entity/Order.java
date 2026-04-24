package com.ecommerce.orders.entity;

import com.ecommerce.admins.entity.Admin;
import com.ecommerce.common.BaseEntity;
import com.ecommerce.common.enums.OrderStatus;
import com.ecommerce.common.enums.ProductStatus;
import com.ecommerce.products.entity.Product;
import com.ecommerce.users.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderId;

	@Column(nullable = false)
	private String number;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private OrderStatus status = OrderStatus.READY;

	@Column(nullable = false)
	private Long quantity;

	@Column(nullable = false)
	private Long totalPrice;

	private String cancelReason;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_Id", nullable = false)
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "admin_Id", nullable = false)
	private Admin admin;

	public Order(String number, Long quantity, Long totalPrice, User user, Product product, Admin admin) {
		this.number = number;
		this.quantity = quantity;
		this.totalPrice = totalPrice;
		this.user = user;
		this.product = product;
		this.admin = admin;
	}

	public void changeStatus(OrderStatus status) {
		this.status = status;
	}

}
