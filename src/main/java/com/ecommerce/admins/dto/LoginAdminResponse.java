package com.ecommerce.admins.dto;

import java.time.LocalDateTime;

import com.ecommerce.common.enums.AdminStatus;

import lombok.Getter;

@Getter
public class LoginAdminResponse {

	private final Long adminId;
	private final String email;
	private final String role;
	private final String tokenType;
	private final String accessToken;

	public LoginAdminResponse(Long adminId, String email, String role, String accessToken) {
		this.adminId = adminId;
		this.email = email;
		this.role = role;
		this.tokenType = "Bearer";
		this.accessToken = accessToken;
	}
}
