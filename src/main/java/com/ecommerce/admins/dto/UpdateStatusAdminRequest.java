package com.ecommerce.admins.dto;

import com.ecommerce.common.enums.AdminStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UpdateStatusAdminRequest {

	@NotBlank(message = "상태는 필수로 작성해야합니다.")
	@Pattern(regexp = "^(ACTIVE|INACTIVE|SUSPENDED|PENDING|REJECTED)$", message = "ACTIVE|INACTIVE|SUSPENDED|PENDING|REJECTED중에서 선택해야 합니다.")
	private final String status;

	public UpdateStatusAdminRequest(String status) {
		this.status = status;
	}
}
