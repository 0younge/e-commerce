package com.ecommerce.users.dto;

import com.ecommerce.common.enums.UserStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class PatchUserStatusRequest {

	@NotBlank(message = "상태는 필수로 작성해야합니다.")
	@Pattern(regexp = "^(ACTIVE|INACTIVE|SUSPENDED)$", message = "ACTIVE|INACTIVE|SUSPENDED중에서 선택해야 합니다.")
	private final UserStatus userStatus;

	public PatchUserStatusRequest(UserStatus userStatus) {
		this.userStatus = userStatus;
	}
}
