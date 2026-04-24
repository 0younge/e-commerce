package com.ecommerce.users.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.common.enums.UserStatus;
import com.ecommerce.users.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query("SELECT u FROM User u " +
		"WHERE (:keyword IS NULL OR u.name LIKE %:keyword% OR u.email LIKE %:keyword%) " +
		"AND (:status IS NULL OR u.status = :status)")
	Page<User> findByKeywordAndStatus(
		@Param("keyword") String keyword,
		@Param("status") UserStatus status,
		Pageable pageable
	);
}
