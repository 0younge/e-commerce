package com.ecommerce.orders.entity;

import com.ecommerce.admins.entity.Admin;
import com.ecommerce.common.BaseEntity;
import com.ecommerce.products.entity.Product;
import com.ecommerce.users.entity.User;

import jakarta.persistence.Entity;
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

	// 위아래 사이 이 주석 밑에 필드값 추가하시면 됩니다!


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productId")
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "adminId")
	private Admin admin;

}
