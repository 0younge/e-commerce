package com.ecommerce.products.entity;

import com.ecommerce.admins.entity.Admin;
import com.ecommerce.common.BaseEntity;
import com.ecommerce.common.enums.ProductStatus;
import com.ecommerce.common.exception.InvalidRequestException;

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

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ProductStatus status = ProductStatus.FOR_SALE;;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "admin_id")
	private Admin admin;

	public Product(String name, String category, Long price, Long quantity, Admin admin) {
		this.name = name;
		this.category = category;
		this.price = price;
		this.quantity = quantity;
		this.admin = admin;
	}

	/**
	 * 상품 정보 수정 (ProductService에서 사용)
	 * Admin 포함 수정
	 */
	public void update(String name, String category, Long price, Admin admin) {
		this.name = name;
		this.category = category;
		this.price = price;
		this.admin = admin;
	}

	/**
	 * 재고 정보 수정 (ProductService에서 사용)
	 * Request 객체로 전체 업데이트 + 상태 자동 변경
	 */
	/**
	 * 재고만 변경 + 상태 자동 갱신
	 *
	 * @param quantity 변경할 재고 수량
	 */
	public void updateQuantity(Long quantity) {

		if (quantity < 0) {
			throw new InvalidRequestException("재고는 0 이상이어야 합니다.");
		}
		this.quantity = quantity;

		// DISCONTINUED 상태가 아닐 때만 자동 변경
		if (this.status != ProductStatus.DISCONTINUED) {
			if (this.quantity == 0) {
				this.status = ProductStatus.SOLD_OUT;
			} else if (this.quantity > 0) {
				this.status = ProductStatus.FOR_SALE;
			}
		}
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
			this.status = ProductStatus.SOLD_OUT;  // ✅ 변경
		}
	}

	/**
	 * 재고 증가 (주문 취소 시)
	 */
	public void increaseQuantity(Long quantity) {
		this.quantity += quantity;

		// 재고 생기면 판매중으로 변경
		if (this.status == ProductStatus.SOLD_OUT) {  // ✅ 변경
			this.status = ProductStatus.FOR_SALE;  // ✅ 변경
		}
	}

	/**
	 * 판매 가능 여부
	 */
	public boolean isAvailable() {
		return this.status == ProductStatus.FOR_SALE && this.quantity > 0;  // ✅ 변경
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
	public void changeStatus(ProductStatus status) {  // ✅ 파라미터 타입 변경
		this.status = status;
	}
}
