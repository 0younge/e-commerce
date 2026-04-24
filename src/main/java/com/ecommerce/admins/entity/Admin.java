package com.ecommerce.admins.entity;

import java.time.LocalDateTime;

import com.ecommerce.common.BaseEntity;
import com.ecommerce.common.enums.AdminStatus;

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
@Table(name = "admins")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long adminId;

	@Column(nullable = false)
	private String name;
	@Column(nullable = false, unique = true)
	private String email;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private String phoneNumber;
	@Column(nullable = false)
	private AdminRole role;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AdminStatus status = AdminStatus.PENDING;

	private String rejectionReason;
	private LocalDateTime approvedAt;
	private LocalDateTime rejectedAt;

	public Admin(String name, String email, String password, String phoneNumber, AdminRole role) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.role = role;
	}

}
