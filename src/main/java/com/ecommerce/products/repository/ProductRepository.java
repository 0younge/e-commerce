package com.ecommerce.products.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.products.entity.Product;
import com.ecommerce.products.enums.ProductStatus;

/**
 * 상품 Repository
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	/**
	 * 상품 검색 (동적 조건)
	 *
	 * @param name 상품명 (부분 검색, null 가능)
	 * @param category 카테고리 (정확히 일치, null 가능)
	 * @param status 상태 (정확히 일치, null 가능)
	 * @param pageable 페이징 정보
	 * @return 검색된 상품 목록 (페이징)
	 */
	@Query("SELECT p FROM Product p WHERE " +
		"(:name IS NULL OR p.name LIKE CONCAT('%', :name, '%')) AND " +
		"(:category IS NULL OR p.category = :category) AND " +
		"(:status IS NULL OR p.status = :status)")

	Page<Product> searchProducts(
		@Param("name") String name,
		@Param("category") String category,
		@Param("status") ProductStatus status,
		Pageable pageable
	);
}