
package com.ecommerce.products.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.products.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query("SELECT p FROM Product p WHERE " +
		"(:name IS NULL OR p.name LIKE CONCAT('%', :name, '%')) AND " +
		"(:category IS NULL OR p.category = :category) AND " +
		"(:status IS NULL OR p.status = :status)")
	Page<Product> searchProducts(
		@Param("name") String name,
		@Param("category") String category,
		@Param("status") String status,  // ← ProductStatus → String
		Pageable pageable
	);
}

