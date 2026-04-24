package com.ecommerce.products.entity;

import java.math.BigDecimal;

import com.ecommerce.common.BaseEntity;
import com.ecommerce.common.enums.ProductStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

	@Column(name = "admin_id", nullable = false)
	private Long adminId;

	@Column(nullable = false, length = 255)
	private String name;

	@Column(nullable = false, length = 50)
	private String category;

	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal price;

	@Column(nullable = false)
	private Integer quantity;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private ProductStatus status;

	public Product(Long adminId, String name, String category,
		BigDecimal price, Integer quantity) {
		this.adminId = adminId;
		this.name = name;
		this.category = category;
		this.price = price;
		this.quantity = quantity;
		this.status = (quantity > 0)
			? ProductStatus.FOR_SALE
			: ProductStatus.SOLD_OUT;
	}

	// 재고 차감
	public void decreaseQuantity(Integer amount) {
		if (this.quantity < amount) {
			throw new IllegalArgumentException("재고가 부족합니다.");
		}
		this.quantity -= amount;

		if (this.quantity <= 0 && this.status != ProductStatus.DISCONTINUED) {
			this.status = ProductStatus.SOLD_OUT;  // ← 수정
		}
	}

	// 재고 증가
	public void increaseQuantity(Integer amount) {
		this.quantity += amount;

		if (this.quantity > 0 && this.status == ProductStatus.SOLD_OUT) {
			this.status = ProductStatus.FOR_SALE;  // ← 수정
		}
	}

	// 재고 변경
	public void updateQuantity(Integer newQuantity) {
		this.quantity = newQuantity;

		if (this.status != ProductStatus.DISCONTINUED) {
			this.status = (newQuantity > 0)
				? ProductStatus.FOR_SALE    // ← 수정
				: ProductStatus.SOLD_OUT;   // ← 수정
		}
	}

	// 상태 변경
	public void updateStatus(ProductStatus newStatus) {
		this.status = newStatus;
	}

	// 정보 수정
	public void update(String name, String category, BigDecimal price) {
		if (name != null && !name.isBlank()) {
			this.name = name;
		}
		if (category != null && !category.isBlank()) {
			this.category = category;
		}
		if (price != null) {
			this.price = price;
		}
	}
}
