package com.ecommerce.common.security.auth;

import com.ecommerce.admins.entity.AdminRole;

public record SecurityAdminInfo(
	Long adminId,
	String email,
	AdminRole role
) {}
