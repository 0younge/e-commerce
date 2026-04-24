package com.ecommerce.products.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.ecommerce.admins.entity.AdminInfo;
import com.ecommerce.products.entity.Product;

/**
 * 상품 응답 DTO (간단 정보)
 * 목록 조회 및 등록 시 사용
 *
 * Record 사용:
 * - Java 14부터 지원되는 불변 데이터 클래스
 * - Getter, equals, hashCode, toString 자동 생성
 * - final 필드, 불변 객체
 *
 * 사용 예시:
 * ProductResponse response = new ProductResponse(1L, 1L, "상품명", ...);
 * Long id = response.productId();  // Getter: 필드명()으로 호출
 * String name = response.name();
 */
public record ProductResponse(
	Long productId,
	Long adminId,
	String name,
	String category,
	BigDecimal price,
	Integer quantity,
	String status,

	LocalDateTime createdAt,
	LocalDateTime modifiedAt
) {

	/**
	 * Product Entity를 ProductResponse로 변환
	 *
	 * 사용 예시:
	 * Product product = productRepository.findById(1L).get();
	 * ProductResponse response = ProductResponse.from(product);
	 *
	 * @param product 상품 Entity
	 * @return ProductResponse DTO
	 */
	public static ProductResponse from(Product product) {
		return new ProductResponse(
			product.getProductId(),
			product.getAdminId(),
			product.getName(),
			product.getCategory(),
			product.getPrice(),
			product.getQuantity(),
			product.getStatus().name(),
			product.getCreatedAt(),
			product.getModifiedAt()
		);
	}
}