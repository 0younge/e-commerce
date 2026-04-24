package com.ecommerce.admins.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.admins.entity.Admin;
import com.ecommerce.admins.entity.AdminRole;
import com.ecommerce.common.enums.AdminStatus;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

	boolean existsByEmail(String email);

	Optional<Admin> findByEmail(String email);

	@Query("SELECT a FROM Admin a WHERE " +
		"(:keyword IS NULL OR a.name LIKE %:keyword% OR a.email LIKE %:keyword%) AND " +
		"(:role IS NULL OR a.role = :role) AND " +
		"(:status IS NULL OR a.status = :status)")
	Page<Admin> findAllByCondition(
		@Param("keyword") String keyword,
		@Param("role") AdminRole role,
		@Param("status") AdminStatus status,
		Pageable pageable
	);
}
