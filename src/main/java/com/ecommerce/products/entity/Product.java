package com.ecommerce.products.entity;

import com.ecommerce.admins.entity.Admin;
import com.ecommerce.common.BaseEntity;
import com.ecommerce.common.exception.InvalidRequestException;

import jakarta.persistence.Column;
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
@Table(name = "products")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;

	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String category;
	@Column(nullable = false)
	private Long price;
	@Column(nullable = false)
	private Long quantity;
	@Column(nullable = false)
	private String status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "admin_id")
	private Admin admin;

	public Product(String name, String category, Long price, Long quantity, String status, Admin admin) {
		this.name = name;
		this.category = category;
		this.price = price;
		this.quantity = quantity;
		this.status = status;
		this.admin = admin;
	}

	public void Update(String name, String category, Long price, Admin admin) {
		this.name = name;
		this.category = category;
		this.price = price;
		this.admin = admin;
	}

	/**
	 * 재고 감소 (주문 시)
	 */
	public void decreaseQuantity(Long quantity) {
		if (this.quantity < quantity) {
			throw new InvalidRequestException("재고가 부족합니다. 현재 재고: " + this.quantity);
		}
		this.quantity -= quantity;

		// 재고 0이면 상태 변경
		if (this.quantity == 0) {
			this.status = "SOLD_OUT";
		}
	}

	/**
	 * 재고 증가 (주문 취소 시)
	 */
	public void increaseQuantity(Long quantity) {
		this.quantity += quantity;

		// 재고 생기면 판매중으로 변경
		if (this.status.equals("SOLD_OUT")) {
			this.status = "FOR_SALE";
		}
	}

	/**
	 * 판매 가능 여부
	 */
	public boolean isAvailable() {
		return this.status.equals("FOR_SALE") && this.quantity > 0;
	}

	/**
	 * 재고 확인
	 */
	public boolean hasStock(Long quantity) {
		return this.quantity >= quantity;
	}

	/**
	 * 상태 변경
	 */
	public void changeStatus(String status) {
		this.status = status;
	}

}
