package com.ecommerce.review.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.review.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
	@Query("SELECT r FROM Review r JOIN  r.user u JOIN r.product p " +
		"WHERE (:keyword IS NULL OR u.name LIKE %:keyword% OR p.name LIKE %:keyword%) " +
		"AND (:rating IS NULL OR r.rating = :rating)")
	Page<Review> findByKeywordAndRating(
		@Param("keyword") String keyword,
		@Param("rating") Integer rating,
		Pageable pageable
	);
}
