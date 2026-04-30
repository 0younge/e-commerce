package com.ecommerce.admins.entity;

import lombok.Getter;

@Getter
public class AdminInfo {

	private final Long adminId;
	private final String email;
	private final AdminRole role;

	public AdminInfo(Long adminId, String email, AdminRole role) {
		this.adminId = adminId;
		this.email = email;
		this.role = role;
	}
}
