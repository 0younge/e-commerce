package com.ecommerce.admins.dto;

import java.time.LocalDateTime;

import com.ecommerce.admins.entity.Admin;
import com.ecommerce.admins.entity.AdminRole;
import com.ecommerce.common.enums.AdminStatus;

import lombok.Getter;

@Getter
public class LoginAdminResponse {

	private final Long adminId;
	private final String email;
	private final AdminRole role;
	private final String tokenType;
	private final String accessToken;

	public LoginAdminResponse(Long adminId, String email, AdminRole role, String accessToken) {
		this.adminId = adminId;
		this.email = email;
		this.role = role;
		this.tokenType = "Bearer";
		this.accessToken = accessToken;
	}

	public static LoginAdminResponse from(Admin admin, String accessToken) {
		return new LoginAdminResponse(admin.getAdminId(), admin.getEmail(), admin.getRole(), accessToken);
	}

}
