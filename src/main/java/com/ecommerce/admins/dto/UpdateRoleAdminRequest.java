package com.ecommerce.admins.dto;

import com.ecommerce.admins.entity.AdminRole;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UpdateRoleAdminRequest {

	@NotBlank(message = "역할은 필수로 작성해야합니다.")
	@Pattern(regexp = "^(SUPER_ADMIN|OPERATION_ADMIN|CS_ADMIN)$", message = "SUPER_ADMIN|OPERATION_ADMIN|CS_ADMIN중에서 선택해야 합니다.")
	private final String role;

	public UpdateRoleAdminRequest(String role) {
		this.role = role;
	}
}
