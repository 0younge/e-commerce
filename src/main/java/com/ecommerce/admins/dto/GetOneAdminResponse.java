package com.ecommerce.admins.dto;

import java.time.LocalDateTime;

import com.ecommerce.admins.entity.Admin;
import com.ecommerce.admins.entity.AdminRole;
import com.ecommerce.common.enums.AdminStatus;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;

@Getter
@JsonPropertyOrder({"name", "email", "phoneNumber", "role", "status", "createdAt", "approvedAt"})
public class GetOneAdminResponse {

	private final String name;
	private final String email;
	private final String phoneNumber;
	private final AdminRole role;
	private final AdminStatus status;
	private final LocalDateTime createdAt;
	private final LocalDateTime approvedAt;

	private GetOneAdminResponse(String name, String email, String phoneNumber, AdminRole role, AdminStatus status,
		LocalDateTime createdAt, LocalDateTime approvedAt) {
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.role = role;
		this.status = status;
		this.createdAt = createdAt;
		this.approvedAt = approvedAt;
	}

	public static GetOneAdminResponse from(Admin admin) {
		return new GetOneAdminResponse(admin.getName(), admin.getEmail(), admin.getPhoneNumber(), admin.getRole(),
			admin.getStatus(), admin.getCreatedAt(), admin.getApprovedAt());
	}
}
