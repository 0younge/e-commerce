package com.ecommerce.admins.dto;

import java.time.LocalDateTime;

import com.ecommerce.admins.entity.Admin;
import com.ecommerce.admins.entity.AdminRole;
import com.ecommerce.common.enums.AdminStatus;

import lombok.Getter;

@Getter
public class GetAdminResponse {

	private final Long adminId;
	private final String name;
	private final String email;
	private final String phoneNumber;
	private final AdminRole role;
	private final AdminStatus status;
	private final LocalDateTime createdAt;
	private final LocalDateTime approvedAt;

	private GetAdminResponse(Long adminId, String name, String email, String phoneNumber, AdminRole role,
		AdminStatus status, LocalDateTime createdAt, LocalDateTime approvedAt) {
		this.adminId = adminId;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.role = role;
		this.status = status;
		this.createdAt = createdAt;
		this.approvedAt = approvedAt;
	}

	public static GetAdminResponse from(Admin admin) {
		return new GetAdminResponse(admin.getAdminId(), admin.getName(), admin.getEmail(), admin.getPhoneNumber(),
			admin.getRole(), admin.getStatus(), admin.getCreatedAt(), admin.getApprovedAt());
	}
}
