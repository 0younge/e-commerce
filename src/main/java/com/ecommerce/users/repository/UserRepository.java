package com.ecommerce.users.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.users.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query("SELECT u FROM User u WHERE " +
		"u.name LIKE %:keyword% OR u.email LIKE %:keyword%")
	Page<User> findByKeyword(
		@Param("keyword") String keyword,
		Pageable pageable
	);
}
