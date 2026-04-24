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

/**
 * 상품 Entity
 *
 * 이커머스 플랫폼의 상품 정보를 관리하는 엔티티
 * Admin이 등록/관리하며, 고객은 조회 및 구매 가능
 */
@Getter
@Entity
@Table(name = "products")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

	/**
	 * 상품 ID (PK)
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;

	/**
	 * 등록한 관리자 ID (FK)
	 * Admin Entity를 직접 참조하지 않고 ID만 저장 (순환참조 방지)
	 */
	@Column(name = "admin_id", nullable = false)
	private Long adminId;

	/**
	 * 상품명
	 */
	@Column(nullable = false, length = 255)
	private String name;

	/**
	 * 카테고리
	 */
	@Column(nullable = false, length = 50)
	private String category;

	/**
	 * 가격
	 * BigDecimal 사용 이유: 금액 계산의 정확성 보장
	 */
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal price;

	/**
	 * 재고 수량
	 */
	@Column(nullable = false)
	private Integer quantity;

	/**
	 * 상품 상태
	 * FOR_SALE: 판매중
	 * SOLD_OUT: 품절
	 * DISCONTINUED: 판매중단
	 */
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private ProductStatus status;

	/**
	 * 상품 생성자
	 *
	 * @param adminId 등록 관리자 ID
	 * @param name 상품명
	 * @param category 카테고리
	 * @param price 가격
	 * @param quantity 재고 수량
	 */
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

	/**
	 * 상품 정보 수정
	 *
	 * @param name 상품명
	 * @param category 카테고리
	 * @param price 가격
	 */
	public void update(String name, String category, BigDecimal price) {
		this.name = name;
		this.category = category;
		this.price = price;
	}

	/**
	 * 재고 수량 변경
	 * 재고에 따라 상품 상태 자동 변경
	 *
	 * @param newQuantity 새로운 재고 수량
	 */
	public void updateQuantity(Integer newQuantity) {
		this.quantity = newQuantity;
		this.status = (newQuantity > 0) ? ProductStatus.FOR_SALE : ProductStatus.SOLD_OUT;
	}

	/**
	 * 재고 감소
	 * 주문 시 호출
	 *
	 * @param amount 감소할 수량
	 * @throws IllegalArgumentException 재고 부족 시
	 */
	public void decreaseQuantity(Integer amount) {
		if (this.quantity < amount) {
			throw new IllegalArgumentException("재고가 부족합니다.");
		}
		this.quantity -= amount;
		if (this.quantity == 0) {
			this.status = ProductStatus.SOLD_OUT;
		}
	}
}
