package com.ecommerce.admins.dto;

import java.time.LocalDateTime;

import com.ecommerce.common.enums.AdminStatus;

import lombok.Getter;

@Getter
public class LoginAdminResponse {

	private final Long adminId;
	private final String name;
	private final String email;
	private final String phoneNumber;
	private final String role;
	private final AdminStatus status;
	private final String tokenType;
	private final String accessToken;
	private final LocalDateTime createdAt;
	private final LocalDateTime approvedAt;

	public LoginAdminResponse(Long adminId, String name, String email, String phoneNumber, String role,
		AdminStatus status, String accessToken, LocalDateTime createdAt, LocalDateTime approvedAt) {
		this.adminId = adminId;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.role = role;
		this.status = status;
		this.tokenType = "Bearer";
		this.accessToken = accessToken;
		this.createdAt = createdAt;
		this.approvedAt = approvedAt;
	}
}
